package com.example.guiitamp;
/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

public enum LiveEffectEngine {

    INSTANCE;

    // Load native library
    static {
        System.loadLibrary("liveEffect");
    }

    // Native methods
    static native boolean create();
    static native boolean isAAudioRecommended();
    static native boolean setAPI(int apiType);
    static native boolean setEffectOn(boolean isEffectOn);
    static native void setRecordingDeviceId(int deviceId);
    static native void setPlaybackDeviceId(int deviceId);
    static native void delete();
    static native void native_setDefaultStreamValues(int defaultSampleRate, int defaultFramesPerBurst);
    // set on/off effects
    static native void setDelayState(boolean value);
    static native void setChorusState(boolean value);
    static native void setReverbState(boolean value);
    static native void setTremoloState(boolean value);
    static native void setFuzzState(boolean value);
    static native void setDistortionState(boolean value);
    // set values
    static native void setGain(float value);
    // Delay
    static native void setDelayTime(double value);
    //Reverb
    static native void setReverbDamping(float value);
    static native void setReverbRoomSize(float value);
    static native void setReverbMode(float value);
    //Chorus
    static native void setChorusModFrequency(float value);
    static native void setChorusEffectMix(float value);
    //Tremolo
    static native void setTremoloDuration(long value);
    static native void setTremoloEffectMix(int value);
    //Fuzz
    static native void setFuzzEffectValue(float value);
    static native void setFuzzEffectMix(int value);
    //Distortion
    static native void setDistortionThreshold(long value);


    static void setDefaultStreamValues(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            AudioManager myAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            String sampleRateStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            int defaultSampleRate = Integer.parseInt(sampleRateStr);
            String framesPerBurstStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
            int defaultFramesPerBurst = Integer.parseInt(framesPerBurstStr);

            native_setDefaultStreamValues(defaultSampleRate, defaultFramesPerBurst);
        }
    }
}
