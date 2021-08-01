package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Reverb extends Pedal {

    public Reverb(Drawable happy, Drawable sad) {
        super(happy, sad);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setReverbMix((float) (state / 10.0));
            }
        }, 10);
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setReverbRoomSize((float) (state / 10.0));
            }
        }, 10);
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setReverbMode((float) (state / 10.0));
            }
        }, 10);
    }

    @Override
    public float getKnobX() {
        return -10;
    }

    @Override
    public float getKnobY() {
        return -20;
    }

    public void triggerEffect(){
        LiveEffectEngine.setReverb(super.isActive);
    }
}
