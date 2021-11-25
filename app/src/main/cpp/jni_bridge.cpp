/**
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

#include <jni.h>
#include <logging_macros.h>
#include "LiveEffectEngine.h"

static const int kOboeApiAAudio = 0;
static const int kOboeApiOpenSLES = 1;

static LiveEffectEngine *engine = nullptr;

extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_example_guiitamp_LiveEffectEngine_create(JNIEnv *env,
                                                  jclass) {
    if (engine == nullptr) {
        engine = new LiveEffectEngine();
    }

    return (engine != nullptr) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_delete(JNIEnv *env,
                                                  jclass) {
    if (engine) {
        engine->setEffectOn(false);
        delete engine;
        engine = nullptr;
    }
}

JNIEXPORT jboolean JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setEffectOn(
        JNIEnv *env, jclass, jboolean isEffectOn) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return JNI_FALSE;
    }

    return engine->setEffectOn(isEffectOn) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setRecordingDeviceId(
        JNIEnv *env, jclass, jint deviceId) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->setRecordingDeviceId(deviceId);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setPlaybackDeviceId(
        JNIEnv *env, jclass, jint deviceId) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->setPlaybackDeviceId(deviceId);
}

JNIEXPORT jboolean JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setAPI(JNIEnv *env,
                                                  jclass type,
                                                  jint apiType) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine "
                "before calling this method");
        return JNI_FALSE;
    }

    oboe::AudioApi audioApi;
    switch (apiType) {
        case kOboeApiAAudio:
            audioApi = oboe::AudioApi::AAudio;
            break;
        case kOboeApiOpenSLES:
            audioApi = oboe::AudioApi::OpenSLES;
            break;
        default:
            LOGE("Unknown API selection to setAPI() %d", apiType);
            return JNI_FALSE;
    }

    return engine->setAudioApi(audioApi) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_example_guiitamp_LiveEffectEngine_isAAudioRecommended(
        JNIEnv *env, jclass type) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine "
                "before calling this method");
        return JNI_FALSE;
    }
    return engine->isAAudioRecommended() ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_native_1setDefaultStreamValues(JNIEnv *env,
                                                                          jclass type,
                                                                          jint sampleRate,
                                                                          jint framesPerBurst) {
    oboe::DefaultStreamValues::SampleRate = (int32_t) sampleRate;
    oboe::DefaultStreamValues::FramesPerBurst = (int32_t) framesPerBurst;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setGain(JNIEnv *env, jclass clazz,
                                                   jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.setGainValue(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setDelayState(JNIEnv *env, jclass clazz,
                                                         jboolean value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.delay = value;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setChorusState(JNIEnv *env, jclass clazz,
                                                          jboolean value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.resetChorus();
    engine->mFullDuplexPass.chorus = value;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setTremoloState(JNIEnv *env, jclass clazz,
                                                           jboolean value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.tremolo = value;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setReverbState(JNIEnv *env, jclass clazz,
                                                          jboolean value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.reverb = value;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setDelayTime(JNIEnv *env, jclass clazz,
                                                        jdouble value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.resetDelay();
    engine->mFullDuplexPass.simpleDelay->setDelayTime(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setReverbDamping(JNIEnv *env, jclass clazz,
                                                            jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.simpleReverb.setDamping(value);
}


JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setReverbRoomSize(JNIEnv *env, jclass clazz,
                                                             jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.simpleReverb.setRoomSize(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setReverbMode(JNIEnv *env, jclass clazz,
                                                         jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.simpleReverb.setWidth(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setChorusModFrequency(JNIEnv *env, jclass clazz,
                                                                 jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.simpleChorus.setModFrequency(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setChorusEffectMix(JNIEnv *env, jclass clazz,
                                                              jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.simpleChorus.setEffectMix(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setFuzzState(JNIEnv *env, jclass clazz, jboolean value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.fuzz = value;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setDistortionState(JNIEnv *env, jclass clazz,
                                                              jboolean value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.distortion = value;
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setTremoloDuration(JNIEnv *env, jclass clazz,
                                                              jlong value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.setTremoloDuration(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setTremoloEffectMix(JNIEnv *env, jclass clazz,
                                                               jint value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.setTremoloEffectMix(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setFuzzEffectValue(JNIEnv *env, jclass clazz,
                                                              jfloat value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.setFuzzEffectValue(value);
}

JNIEXPORT void JNICALL
Java_com_example_guiitamp_LiveEffectEngine_setDistortionThreshold(JNIEnv *env, jclass clazz,
                                                                  jlong value) {
    if (engine == nullptr) {
        LOGE(
                "Engine is null, you must call createEngine before calling this "
                "method");
        return;
    }

    engine->mFullDuplexPass.setDistortionThreshold(value);
}

}