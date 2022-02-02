/*
 * Copyright 2019 The Android Open Source Project
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

#ifndef OBOE_FULL_DUPLEX_STREAM_H
#define OBOE_FULL_DUPLEX_STREAM_H

#include <unistd.h>
#include <sys/types.h>

#include "oboe/Oboe.h"
#include "TSKEffects/Chorus.h"
#include "TSKEffects/TwoPole.h"
#include "TSKEffects/FreeVerb.h"
#include "AEfromArduino/AudioEffect.h"
#include "DelayEffects/SimpleDelay.hpp"
#include "DelayEffects/SimpleFlanger.hpp"


class FullDuplexStream : public oboe::AudioStreamCallback {
public:
    FullDuplexStream() {}
    virtual ~FullDuplexStream() = default;

    //GAIN
    float gainValue = 1.0;

    void setGainValue(float value){
        gainValue = value;
    }

    //DELAY
    bool delay = false;

    void resetDelay(){
        simpleDelay = new SimpleDelay(44099, 44100);
    }

    SimpleDelay *simpleDelay = new SimpleDelay(44099, 44100);

    //TREMOLO
    bool tremolo = false;
    void setTremoloDuration(int16_t duration){
        tremoloDuration_ms = duration;
        simpleTremolo->updateDuration(tremoloDuration_ms);
        //resetTremolo();
    }
    void setTremoloEffectMix(u_int8_t percent){
        depthPercent = percent;
        //resetTremolo();
    }

    void resetTremolo(){
        simpleTremolo = new audio_tools::Tremolo(tremoloDuration_ms,depthPercent);
    }

    int16_t tremoloDuration_ms=500;
    u_int8_t depthPercent=50;
    audio_tools::Tremolo *simpleTremolo = new audio_tools::Tremolo(tremoloDuration_ms,depthPercent);

    //FUZZ
    bool fuzz = false;
    void setFuzzEffectValue(float value){
        fuzzEffectValue = value;
        //resetFuzz();
    }
    void setFuzzEffectMix(int16_t value){
        effectMix = value;
        //resetFuzz();
    }
    void resetFuzz(){
        simpleFuzz = new audio_tools::Fuzz(fuzzEffectValue, effectMix);
    }
    u_int16_t effectMix=500;
    float fuzzEffectValue = 5.0;
    audio_tools::Fuzz *simpleFuzz = new audio_tools::Fuzz(fuzzEffectValue, effectMix);

    //DISTORTION
    bool distortion = false;
    void setDistortionThreshold(int16_t threshold){
        clipThreshold = threshold;
        resetDistortion();
    }
    void resetDistortion(){
        simpleDistortion = new audio_tools::Distortion(clipThreshold);
    }
    int16_t clipThreshold = 2000;
    audio_tools::Distortion *simpleDistortion = new audio_tools::Distortion(clipThreshold);

    //CHORUS
    bool chorus = false;

    void resetChorus(){
        simpleChorus.clear();
    }

    //stk::Chorus simpleChorus = {6000};
    stk::Chorus simpleChorus;

    //REVERB
    bool reverb = false;

    stk::FreeVerb simpleReverb;



    void setInputStream(std::shared_ptr<oboe::AudioStream> stream) {
        mInputStream = stream;
    }

    void setOutputStream(std::shared_ptr<oboe::AudioStream> stream) {
        mOutputStream = stream;
    }

    virtual oboe::Result start();

    virtual oboe::Result stop();

    /**
     * Called when data is available on both streams.
     * App should override this method.
     */
    virtual oboe::DataCallbackResult onBothStreamsReady(
            std::shared_ptr<oboe::AudioStream> inputStream,
            const void *inputData,
            int   numInputFrames,
            std::shared_ptr<oboe::AudioStream> outputStream,
            void *outputData,
            int   numOutputFrames) = 0;

    /**
     * Called by Oboe when the stream is ready to process audio.
     * This implements the stream synchronization. App should NOT override this method.
     */
    oboe::DataCallbackResult onAudioReady(
            oboe::AudioStream *audioStream,
            void *audioData,
            int numFrames) override;

    int32_t getNumInputBurstsCushion() const;

    /**
     * Number of bursts to leave in the input buffer as a cushion.
     * Typically 0 for latency measurements
     * or 1 for glitch tests.
     *
     * @param mNumInputBurstsCushion
     */
    void setNumInputBurstsCushion(int32_t numInputBurstsCushion);

private:

    // TODO add getters and setters
    static constexpr int32_t kNumCallbacksToDrain   = 20;
    static constexpr int32_t kNumCallbacksToDiscard = 30;

    // let input fill back up, usually 0 or 1
    int32_t              mNumInputBurstsCushion = 1;

    // We want to reach a state where the input buffer is empty and
    // the output buffer is full.
    // These are used in order.
    // Drain several callback so that input is empty.
    int32_t              mCountCallbacksToDrain = kNumCallbacksToDrain;
    // Let the input fill back up slightly so we don't run dry.
    int32_t              mCountInputBurstsCushion = mNumInputBurstsCushion;
    // Discard some callbacks so the input and output reach equilibrium.
    int32_t              mCountCallbacksToDiscard = kNumCallbacksToDiscard;

    std::shared_ptr<oboe::AudioStream> mInputStream;
    std::shared_ptr<oboe::AudioStream> mOutputStream;

    int32_t              mBufferSize = 0;
    std::unique_ptr<float[]> mInputBuffer;
};


#endif //OBOE_FULL_DUPLEX_STREAM_H
