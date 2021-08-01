package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class BasicAmplifier extends Pedal{

    public BasicAmplifier(Drawable happy, Drawable sad) {
        super(happy, sad);
    }

    @Override
    public float getKnobX() {
        return 0;
    }

    @Override
    public float getKnobY() {
        return 0;
    }

    @Override
    public void triggerEffect() {
        return;
    }
}
