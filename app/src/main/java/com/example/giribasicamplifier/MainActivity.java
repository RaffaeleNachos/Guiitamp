package com.example.giribasicamplifier;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;

import com.google.android.material.slider.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int AUDIO_PERMISSION_CODE = 1;

    private static final String APPNAME = MainActivity.class.getName();

    private boolean isPlaying = false;
    private ImageButton powerSwitch;
    private ImageButton sideMenuBtn;
    private Slider gainSlider;
    private RecyclerView pedalsBtnList;
    private PedalAdapter pedalAdapter;
    private ArrayList<Pedal> availablePedals;
    private SideMenu sideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sideMenu = new SideMenu();

        sideMenuBtn = findViewById(R.id.side_menu_btn);
        sideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.side_menu_fragment, new SideMenu()).commit();
            }
        });

        checkPermission( Manifest.permission.RECORD_AUDIO, AUDIO_PERMISSION_CODE);

        // set up pedals list view with supported Pedals
        pedalsBtnList = findViewById(R.id.pedalsList);
        pedalsBtnList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        availablePedals = new ArrayList<>();
        pedalAdapter = new PedalAdapter();
        this.setUpPedalList();

        powerSwitch = findViewById(R.id.togglePower);
        powerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEffect();
            }
        });

        gainSlider = findViewById(R.id.gainSlider);
        gainSlider.setValue(1);
        gainSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                LiveEffectEngine.setGain(value);
            }
        });

        LiveEffectEngine.setDefaultStreamValues(this);
    }

    private void setUpPedalList(){
        availablePedals.add(new Chorus(this.getDrawable(R.drawable.chorus)));
        availablePedals.add(new Delay(this.getDrawable(R.drawable.delay)));
        availablePedals.add(new Reverb(this.getDrawable(R.drawable.reverb)));
        availablePedals.add(new Echo(this.getDrawable(R.drawable.echo)));
        pedalAdapter.setPedalList(availablePedals);
        pedalsBtnList.setAdapter(pedalAdapter);
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
//            Toast.makeText(MainActivity.this,
//                    "Permission already granted",
//                    Toast.LENGTH_SHORT)
//                    .show();
        }
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
/*        sideMenu.mAAudioRecommended = LiveEffectEngine.isAAudioRecommended();
        sideMenu.EnableAudioApiUI(true);*/
        LiveEffectEngine.setAPI(sideMenu.apiSelection);
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
            LiveEffectEngine.setAPI(sideMenu.apiSelection);
            startEffect();
        }
    }


    private void stopEffect() {
        Log.d(APPNAME, "Stop Effect");
        LiveEffectEngine.setEffectOn(false);
        powerSwitch.setImageResource(R.drawable.ic_switch_off);
        pedalAdapter.notifyDataSetChanged();
        isPlaying = false;
        sideMenu.setSpinnersEnabled(true);
    }

    private void startEffect() {
        Log.d(APPNAME, "Start Effect");

        boolean success = LiveEffectEngine.setEffectOn(true);
        if (success) {
            sideMenu.setSpinnersEnabled(false);
            powerSwitch.setImageResource(R.drawable.ic_switch_on);
            pedalAdapter.notifyDataSetChanged();
            isPlaying = true;
        } else {
            isPlaying = false;
        }
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
                //permission granted
            } else {
                //graceful degrade
            }
        }
    }
}