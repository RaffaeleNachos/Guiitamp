package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

import it.beppi.knoblibrary.Knob;

public class Reverb extends Pedal {

    public Reverb(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new HashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setReverbDamping((float) (state * 0.05));
            }
        }, new PedalInfo(20,5, "DAMPING")); // StockValue: 0.25   Range: 0 -> 1
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setReverbRoomSize((float) (state * 0.05));
            }
        }, new PedalInfo(20,15, "ROOM SIZE")); // StockValue: 0.75  Range: 0 -> 1
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                LiveEffectEngine.setReverbMode((float) (state * 0.05));
            }
        }, new PedalInfo(20,20, "MODE")); // StockValue: 1.0   Range: 0 -> 1
    }

    @Override
    public void triggerEffect(){
        LiveEffectEngine.setReverbState(super.isActive);
    }
}
