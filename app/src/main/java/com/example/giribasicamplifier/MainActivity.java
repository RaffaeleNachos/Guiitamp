package com.example.giribasicamplifier;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.slider.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int AUDIO_PERMISSION_CODE = 1;

    private static final String APPNAME = MainActivity.class.getName();

    private static final int OBOE_API_AAUDIO = 0;
    private static final int OBOE_API_OPENSL_ES = 1;

    private int apiSelection = OBOE_API_AAUDIO;
    private boolean mAAudioRecommended = true;
    private boolean isPlaying = false;
    private Button toggleEffectButton;
    private Slider gainSlider;
    private AudioDeviceSpinner recordingDeviceSpinner;
    private AudioDeviceSpinner playbackDeviceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggleEffectButton = findViewById(R.id.toggleEffect);

        toggleEffectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(
                        Manifest.permission.RECORD_AUDIO,
                        AUDIO_PERMISSION_CODE);
                toggleEffect();
            }
        });
        toggleEffectButton.setText("START");
        gainSlider = findViewById(R.id.gainSlider);

        recordingDeviceSpinner = findViewById(R.id.inputSpinner);
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

        playbackDeviceSpinner = findViewById(R.id.outputSpinner);
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

        gainSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                LiveEffectEngine.setGain(value);
            }
        });


        ((RadioGroup) findViewById(R.id.radioContainer)).check(R.id.aaudio);
        findViewById(R.id.aaudio).setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    apiSelection = OBOE_API_AAUDIO;
                }
            }
        });
        findViewById(R.id.openSLES).setOnClickListener(new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((RadioButton) v).isChecked()) {
                    apiSelection = OBOE_API_OPENSL_ES;
                }
            }
        });

        LiveEffectEngine.setDefaultStreamValues(this);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
            Toast.makeText(MainActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void EnableAudioApiUI(boolean enable) {
        if (apiSelection == OBOE_API_AAUDIO && !mAAudioRecommended) {
            apiSelection = OBOE_API_OPENSL_ES;
        }
        findViewById(R.id.openSLES).setEnabled(enable);
        if (!mAAudioRecommended) {
            findViewById(R.id.aaudio).setEnabled(false);
        } else {
            findViewById(R.id.aaudio).setEnabled(enable);
        }

        ((RadioGroup) findViewById(R.id.radioContainer))
                .check(apiSelection == OBOE_API_AAUDIO ? R.id.aaudio : R.id.openSLES);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LiveEffectEngine.create();
        mAAudioRecommended = LiveEffectEngine.isAAudioRecommended();
        EnableAudioApiUI(true);
        LiveEffectEngine.setAPI(apiSelection);
    }

    @Override
    protected void onPause() {
        stopEffect();
        LiveEffectEngine.delete();
        super.onPause();
    }

    public void toggleEffect() {
        if (isPlaying) {
            stopEffect();
        } else {
            LiveEffectEngine.setAPI(apiSelection);
            startEffect();
        }
    }


    private void stopEffect() {
        Log.d(APPNAME, "Stop Effect");
        LiveEffectEngine.setEffectOn(false);
        toggleEffectButton.setText("START");
        isPlaying = false;
        setSpinnersEnabled(true);
        EnableAudioApiUI(true);
    }

    private void startEffect() {
        Log.d(APPNAME, "Start Effect");

//        if (!isRecordPermissionGranted()){
//            requestRecordPermission();
//            return;
//        }

        boolean success = LiveEffectEngine.setEffectOn(true);
        if (success) {
            setSpinnersEnabled(false);
            toggleEffectButton.setText("STOP");
            isPlaying = true;
            EnableAudioApiUI(false);
        } else {
            isPlaying = false;
        }
    }

    private void setSpinnersEnabled(boolean isEnabled) {
        recordingDeviceSpinner.setEnabled(isEnabled);
        playbackDeviceSpinner.setEnabled(isEnabled);
    }

    private int getRecordingDeviceId() {
        return ((AudioDeviceListEntry) recordingDeviceSpinner.getSelectedItem()).getId();
    }

    private int getPlaybackDeviceId() {
        return ((AudioDeviceListEntry) playbackDeviceSpinner.getSelectedItem()).getId();
    }

    //    microphone permissions
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}