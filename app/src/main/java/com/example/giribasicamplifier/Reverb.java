package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Reverb extends Pedal {

    public Reverb(Drawable pedalImg) {
        super(pedalImg);
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

    public void triggerEffect(){
        LiveEffectEngine.setReverb(super.isActive);
    }
}
