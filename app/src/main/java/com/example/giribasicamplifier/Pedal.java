package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;

public class Pedal {

    private Drawable happy;
    private Drawable sad;
    public boolean isActive = false;
    public Effect effect;

    public Pedal(Drawable happy, Drawable sad, Effect effect){
        this.happy = happy;
        this.sad = sad;
        this.effect = effect;
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

    public void triggerEffect(){
        switch (effect) {
            case Delay:
                LiveEffectEngine.setDelay(isActive);
                break;
            case Echo:
                LiveEffectEngine.setEcho(isActive);
                break;
            case Reverb:
                LiveEffectEngine.setReverb(isActive);
                break;
            case Flanger:
                LiveEffectEngine.setFlanger(isActive);
                break;
            default:
                break;
        }
    }
}
