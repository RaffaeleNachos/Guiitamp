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
                LiveEffectEngine.setChorusDepth((float) (state / 10.0));
            }
        }, 10);
    }

    public void triggerEffect(){
        LiveEffectEngine.setChorus(super.isActive);
    }
}