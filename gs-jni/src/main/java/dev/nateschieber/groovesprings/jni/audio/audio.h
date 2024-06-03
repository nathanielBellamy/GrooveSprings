#ifndef AUDIO_H
#define AUDIO_H

#include <sndfile.hh>
#include <portaudio.h>
#include "jni.h"
#include "./audio_data.h"
#include "./jni_data.h"

class Audio
{
        JNIEnv* jniEnv;
        char const* fileName;

    public:
        Audio(JNIEnv* env, jstring jFileName);

        int run();

        void freeAudioData(AUDIO_DATA *audioData);

        static void jSetCurrFrameId(JNI_DATA* jniData, int currFrameId);

        static int callback(const void *inputBuffer, void *outputBuffer,
                            unsigned long framesPerBuffer,
                            const PaStreamCallbackTimeInfo* timeInfo,
                            PaStreamCallbackFlags statusFlags,
                            void *userData );
};

#endif