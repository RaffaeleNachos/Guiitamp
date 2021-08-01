package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Chorus extends Pedal {

    public Chorus(Drawable happy, Drawable sad) {
        super(happy, sad);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setChorusDepth((float) (state / 100.0));
            }
        }, 10);
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setModFrequency((float) (1 * state));
            }
        }, 10);
    }

    @Override
    public float getKnobX() {
        return 0;
    }

    @Override
    public float getKnobY() {
        return 60;
    }

    public void triggerEffect(){
        LiveEffectEngine.setChorus(super.isActive);
    }
}