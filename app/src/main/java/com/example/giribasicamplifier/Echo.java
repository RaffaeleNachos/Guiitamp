package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Echo extends Pedal{

    public Echo(Drawable happy, Drawable sad) {
        super(happy, sad);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setEchoTiming((65000 / 40) * state);
            }
        }, 40);
    }

    public void triggerEffect(){
        LiveEffectEngine.setEcho(super.isActive);
    }
}
