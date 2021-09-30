package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public abstract class Pedal {

    public HashMap<Knob.OnStateChanged, PedalStatesInfo> knobs; // HashMap of type <Knob.Callback,PedalStatesInfo>
    private Drawable pedalImg;
    public boolean isActive = false;

    public Pedal(Drawable pedalImg){
        this.pedalImg = pedalImg;
    }

    public Drawable getPedalImg() {
        return pedalImg;
    }

    abstract public void triggerEffect();

    public class PedalStatesInfo{
        public Integer numOfStates;
        public Integer initialState;

        PedalStatesInfo(Integer numOfStates, Integer initialState){
            this.initialState = initialState;
            this.numOfStates = numOfStates;
        }
    }

}
