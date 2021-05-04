package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class Chorus extends Pedal {

    public Chorus(Drawable happy, Drawable sad) {
        super(happy, sad);
    }

    public void triggerEffect(){
        LiveEffectEngine.setChorus(super.isActive);
    }
}