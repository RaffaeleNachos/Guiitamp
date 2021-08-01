package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Delay extends Pedal {

    public Delay(Drawable happy, Drawable sad) {
        super(happy, sad);
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

    @Override
    public float getKnobX() {
        return 0;
    }

    @Override
    public float getKnobY() {
        return 20;
    }

    public void triggerEffect(){
        LiveEffectEngine.setDelay(super.isActive);
    }
}
