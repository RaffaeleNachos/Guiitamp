package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class Pedal {

    private Drawable happy;
    private Drawable sad;
    private boolean isActive = false;

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

    public void setActive(boolean active) {
        isActive = active;
    }
}
