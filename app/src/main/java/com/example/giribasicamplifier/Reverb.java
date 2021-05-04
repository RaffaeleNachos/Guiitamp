package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class Reverb extends Pedal {

    public Reverb(Drawable happy, Drawable sad) {
        super(happy, sad);
    }

    public void triggerEffect(){
        LiveEffectEngine.setReverb(super.isActive);
    }
}
