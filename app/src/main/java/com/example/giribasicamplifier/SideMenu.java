package com.example.giribasicamplifier;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class SideMenu extends Fragment {

    private static final int OBOE_API_AAUDIO = 0;
    private static final int OBOE_API_OPENSL_ES = 1;

    public int apiSelection = OBOE_API_AAUDIO;

    private AudioDeviceSpinner recordingDeviceSpinner;
    private AudioDeviceSpinner playbackDeviceSpinner;

    private RadioButton aaudio;
    private RadioButton opensl_es;

    private ImageButton back;

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

        SharedPreferences sharedPref = getActivity().getSharedPreferences("com.example.giribasicamplifier", Context.MODE_PRIVATE);

        back = view.findViewById(R.id.close_side_menu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(SideMenu.this).commit();
            }
        });

        recordingDeviceSpinner = view.findViewById(R.id.inputSpinner);
        recordingDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_INPUTS);
        recordingDeviceSpinner.setSelection(sharedPref.getInt("recordingDeviceSpinner", 0));
        recordingDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LiveEffectEngine.setRecordingDeviceId(getRecordingDeviceId());
                sharedPref.edit().putInt("recordingDeviceSpinner", recordingDeviceSpinner.getSelectedItemPosition()).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        playbackDeviceSpinner = view.findViewById(R.id.outputSpinner);
        playbackDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_OUTPUTS);
        playbackDeviceSpinner.setSelection(sharedPref.getInt("playbackDeviceSpinner", 0));
        playbackDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LiveEffectEngine.setPlaybackDeviceId(getPlaybackDeviceId());
                sharedPref.edit().putInt("playbackDeviceSpinner", playbackDeviceSpinner.getSelectedItemPosition()).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        ((RadioGroup) view.findViewById(R.id.radioContainer)).check(R.id.aaudio);
        aaudio = view.findViewById(R.id.aaudio);
        aaudio.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    apiSelection = OBOE_API_AAUDIO;
                    sharedPref.edit().putString("apiSelection", "AAUDIO").apply();
                }
            }
        });
        opensl_es = view.findViewById(R.id.openSLES);
        opensl_es.setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    apiSelection = OBOE_API_OPENSL_ES;
                    sharedPref.edit().putString("apiSelection", "OPENSL_ES").apply();
                }
            }
        });
        String apiSel = sharedPref.getString("apiSelection", "AAUDIO");
        if (apiSel.equals("AAUDIO")){
            aaudio.setActivated(true);
            opensl_es.setActivated(false);
        } else {
            aaudio.setActivated(false);
            opensl_es.setActivated(true);
        }
    }


    private int getRecordingDeviceId() {
        return ((AudioDeviceListEntry) recordingDeviceSpinner.getSelectedItem()).getId();
    }

    private int getPlaybackDeviceId() {
        return ((AudioDeviceListEntry) playbackDeviceSpinner.getSelectedItem()).getId();
    }

    public void setSpinnersEnabled(boolean isEnabled) {
        if (recordingDeviceSpinner != null && playbackDeviceSpinner != null) {
            recordingDeviceSpinner.setEnabled(isEnabled);
            playbackDeviceSpinner.setEnabled(isEnabled);
        }
    }
}
