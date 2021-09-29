package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public abstract class Pedal {

    public HashMap<Knob.OnStateChanged, Integer> knobs; // HashMap of type <Knob.Callback,numOfKnobStates>
    private Drawable pedalImg;
    public boolean isActive = false;

    public Pedal(Drawable pedalImg){
        this.pedalImg = pedalImg;
    }

    public Drawable getPedalImg() {
        return pedalImg;
    }

    abstract public void triggerEffect();

}
