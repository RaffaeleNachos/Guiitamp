package com.example.giribasicamplifier;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class SideMenu extends Fragment {

    private static final int OBOE_API_AAUDIO = 0;
    private static final int OBOE_API_OPENSL_ES = 1;

    public int apiSelection = OBOE_API_AAUDIO;
    public boolean mAAudioRecommended = true;

    private AudioDeviceSpinner recordingDeviceSpinner;
    private AudioDeviceSpinner playbackDeviceSpinner;

    public SideMenu(){
        super(R.layout.side_menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.side_menu, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recordingDeviceSpinner = view.findViewById(R.id.inputSpinner);
        recordingDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_INPUTS);
        recordingDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LiveEffectEngine.setRecordingDeviceId(getRecordingDeviceId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        playbackDeviceSpinner = view.findViewById(R.id.outputSpinner);
        playbackDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_OUTPUTS);
        playbackDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LiveEffectEngine.setPlaybackDeviceId(getPlaybackDeviceId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        ((RadioGroup) view.findViewById(R.id.radioContainer)).check(R.id.aaudio);
        view.findViewById(R.id.aaudio).setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    apiSelection = OBOE_API_AAUDIO;
                }
            }
        });
        view.findViewById(R.id.openSLES).setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    apiSelection = OBOE_API_OPENSL_ES;
                }
            }
        });
    }


    private int getRecordingDeviceId() {
        return ((AudioDeviceListEntry) recordingDeviceSpinner.getSelectedItem()).getId();
    }

    private int getPlaybackDeviceId() {
        return ((AudioDeviceListEntry) playbackDeviceSpinner.getSelectedItem()).getId();
    }

    public void setSpinnersEnabled(boolean isEnabled) {
        recordingDeviceSpinner.setEnabled(isEnabled);
        playbackDeviceSpinner.setEnabled(isEnabled);
    }

    public void EnableAudioApiUI(boolean enable) {
        if (apiSelection == OBOE_API_AAUDIO && !mAAudioRecommended) {
            apiSelection = OBOE_API_OPENSL_ES;
        }
        getView().findViewById(R.id.openSLES).setEnabled(enable);
        if (!mAAudioRecommended) {
            getView().findViewById(R.id.aaudio).setEnabled(false);
        } else {
            getView().findViewById(R.id.aaudio).setEnabled(enable);
        }

        ((RadioGroup) getView().findViewById(R.id.radioContainer))
                .check(apiSelection == OBOE_API_AAUDIO ? R.id.aaudio : R.id.openSLES);
    }
}
