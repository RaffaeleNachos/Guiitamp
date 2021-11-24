package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Distortion extends Pedal{

    public Distortion(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                //LiveEffectEngine.setDelayTime(state * 2200);
            }
        }, new PedalInfo(21,20, "TIME")); // StockValue: (44000)   Range: 0 -> (44100)
    }

    @Override
    public void triggerEffect() {
        LiveEffectEngine.setDistortionState(super.isActive);
    }
}
