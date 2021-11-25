package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Fuzz extends Pedal{

    public Fuzz(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setFuzzEffectValue((float) (state * 1.0));
            }
        }, new PedalInfo(11,5, "TIME")); // StockValue: (5.0)   Range: 0 -> (10.0)
    }

    @Override
    public void triggerEffect() {
        LiveEffectEngine.setFuzzState(super.isActive);
    }
}
