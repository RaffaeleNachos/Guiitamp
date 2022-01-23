package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Tremolo extends Pedal{

    public Tremolo(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setTremoloDuration((state + 1) * 100);
            }
        }, new PedalInfo(20,5, "DURATION")); // StockValue: (500)   Range: 100 -> (2000)
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setTremoloEffectMix(state * 10);
            }
        }, new PedalInfo(11,5, "EFFECT MIX")); // StockValue: (50)   Range: 0 -> (100)
    }

    @Override
    public void triggerEffect() {
        LiveEffectEngine.setTremoloState(super.isActive);
    }
}
