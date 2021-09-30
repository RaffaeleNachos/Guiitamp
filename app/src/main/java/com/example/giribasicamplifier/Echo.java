package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Echo extends Pedal{

    public Echo(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setEchoDelay((long) (state * 11025));
            }
        }, new PedalStatesInfo(20,10)); // StockValue: (44100 * 2.5)   Range: 0 -> (44100 * 5)
    }

    public void triggerEffect(){
        LiveEffectEngine.setEcho(super.isActive);
    }
}
