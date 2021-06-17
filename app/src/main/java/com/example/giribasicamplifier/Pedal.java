package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public abstract class Pedal {

    public HashMap<Knob.OnStateChanged, Integer> knobs; // HashMap of type <Knob.Callback,numOfKnobStates>
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
