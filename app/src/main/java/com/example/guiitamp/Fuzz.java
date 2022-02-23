package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.LinkedHashMap;

import it.beppi.knoblibrary.Knob;

public class Fuzz extends Pedal{

    public Fuzz(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new LinkedHashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                //That's triky but I need to change the value of the actual state
                //I'm accessing the effect's instance HashMap, using as key "this", which is the callback, obtaining the PedalInfo instance
                Fuzz.this.knobs.get(this).actualState = state;
                LiveEffectEngine.setFuzzEffectValue((float) (state * 1.0));
            }
        }, new PedalInfo(11,5, "PRESENCE")); // StockValue: (5.0)   Range: 0 -> (10.0)
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                Fuzz.this.knobs.get(this).actualState = state;
                LiveEffectEngine.setFuzzEffectMix((state * 100));
            }
        }, new PedalInfo(11,6, "EFFECT MIX")); // StockValue: (500)   Range: 0 -> 1000
    }

    @Override
    public void triggerEffect() {
        LiveEffectEngine.setFuzzState(super.isActive);
    }
}
