package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public abstract class Pedal {

    private Drawable happy;
    private Drawable sad;
    public boolean isActive = false;

    public Pedal(Drawable happy, Drawable sad){
        this.happy = happy;
        this.sad = sad;
    }

    public void setHappy(Drawable happy) {
        this.happy = happy;
    }

    public void setSad(Drawable sad) {
        this.sad = sad;
    }

    public Drawable getHappy() {
        return happy;
    }

    public Drawable getSad() {
        return sad;
    }

    abstract public void triggerEffect();

}
