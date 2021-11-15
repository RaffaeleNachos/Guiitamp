package com.example.guiitamp;

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
                LiveEffectEngine.setEchoDelay(state * 11025);
            }
        }, new PedalInfo(21,10,"DELAY")); // StockValue: (44100 * 2.5)   Range: 0 -> (44100 * 5)
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setEchoEffectMix((float) (state * 0.1));
            }
        }, new PedalInfo(11,10, "EFFECT MIX")); // StockValue: 1   Range: 0.0 -> 1.0
    }

    public void triggerEffect(){
        LiveEffectEngine.setEcho(super.isActive);
    }
}
