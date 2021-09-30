package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Chorus extends Pedal {

    public Chorus(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setChorusModDepth((float) (state * 0.05));
            }
        }, new PedalStatesInfo(20,1)); // StockValue: 0.05  Range: 0.0 -> 1.0
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setChorusModFrequency((float) (state * 0.1));
            }
        }, new PedalStatesInfo(60,1)); // StockValue: 0.02   Range: 0.1 -> 6
    }

    public void triggerEffect(){
        LiveEffectEngine.setChorus(super.isActive);
    }
}