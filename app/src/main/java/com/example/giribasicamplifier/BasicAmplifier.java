package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class BasicAmplifier extends Pedal{

    public BasicAmplifier(Drawable happy, Drawable sad) {
        super(happy, sad);
    }

    @Override
    public void triggerEffect() {
        return;
    }
}
