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
                LiveEffectEngine.setDistortionThreshold(state * 400);
            }
        }, new PedalInfo(21,5, "THRESHOLD")); // StockValue: (2000)   Range: 0 -> (8000)
    }

    @Override
    public void triggerEffect() {
        LiveEffectEngine.setDistortionState(super.isActive);
    }
}
