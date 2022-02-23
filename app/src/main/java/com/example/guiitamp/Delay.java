package com.example.guiitamp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.LinkedHashMap;

import it.beppi.knoblibrary.Knob;

public class Delay extends Pedal {

    public Delay(Drawable pedalImg) {
        super(pedalImg);
        this.knobs = new LinkedHashMap<>();
        knobs.put(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {
                Delay.this.knobs.get(this).actualState = state;
                LiveEffectEngine.setDelayTime(state * 2200);
            }
        }, new PedalInfo(21,20, "TIME")); // StockValue: (44000)   Range: 0 -> (44100)
    }

    @Override
    public void triggerEffect(){
        LiveEffectEngine.setDelayState(super.isActive);
    }
}
