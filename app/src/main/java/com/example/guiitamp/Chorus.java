package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.LinkedHashMap;

import it.beppi.knoblibrary.Knob;

public class Chorus extends Pedal {

    public Chorus(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new LinkedHashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                Chorus.this.knobs.get(this).actualState = state;
                LiveEffectEngine.setChorusModFrequency((float) (state * 0.05));
            }
        }, new PedalInfo(11,1, "MOD FREQ")); // StockValue: 0.02   Range: 0.00 -> 0.5
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                Chorus.this.knobs.get(this).actualState = state;
                LiveEffectEngine.setChorusEffectMix((float) (state * 0.1));
            }
        }, new PedalInfo(11,10, "EFFECT MIX")); // StockValue: 1   Range: 0.0 -> 1.0
    }

    @Override
    public void triggerEffect(){
        LiveEffectEngine.setChorusState(super.isActive);
    }
}