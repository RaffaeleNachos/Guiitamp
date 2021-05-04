package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class Echo extends Pedal{

    public Echo(Drawable happy, Drawable sad) {
        super(happy, sad);
    }

    public void triggerEffect(){
        LiveEffectEngine.setEcho(super.isActive);
    }
}
