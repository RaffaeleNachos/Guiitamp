package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class Delay extends Pedal {

    public Delay(Drawable happy, Drawable sad) {
        super(happy, sad);
        LiveEffectEngine.setDelayLenght(10);
    }

    public void triggerEffect(){
        LiveEffectEngine.setDelay(super.isActive);
    }
}
