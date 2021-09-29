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
                LiveEffectEngine.setDelayTime(state * 5510);
            }
        }, 40);
        /*knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setDelayTransitionTime(state * 5510);
            }
        }, 40);*/
    }

    public void triggerEffect(){
        LiveEffectEngine.setDelay(super.isActive);
    }
}
