package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Delay extends Pedal {

    public Delay(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setDelayTime(state * 11025);
            }
        }, new PedalStatesInfo(20,4)); // StockValue: (44100 * 1)   Range: 0 -> (44100 * 5)
    }

    public void triggerEffect(){
        LiveEffectEngine.setDelay(super.isActive);
    }
}
