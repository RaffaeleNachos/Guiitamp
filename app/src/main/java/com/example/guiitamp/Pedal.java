package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.LinkedHashMap;

import it.beppi.knoblibrary.Knob;

public abstract class Pedal {

    public LinkedHashMap<Knob.OnStateChanged, PedalInfo> knobs; // HashMap of type <Knob.Callback,PedalStatesInfo>
    private Drawable pedalImg;
    public boolean isActive = false;

    public Pedal(Drawable pedalImg){
        this.pedalImg = pedalImg;
    }

    public Drawable getPedalImg() {
        return pedalImg;
    }

    abstract public void triggerEffect();

    public class PedalInfo {
        public Integer numOfStates;
        public Integer initialState;
        public Integer actualState;
        public String controlName;

        PedalInfo(Integer numOfStates, Integer initialState, String controlName){
            this.initialState = initialState;
            this.numOfStates = numOfStates;
            this.controlName = controlName;
            this.actualState = initialState;
        }
    }

}
