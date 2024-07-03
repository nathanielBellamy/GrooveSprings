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
        jlong initialFrameId;

    public:
        Audio(JNIEnv* env, jstring jFileName, jlong initialFrameId);

        int run();

        void freeAudioData(AUDIO_DATA *audioData);

        static void jSetCurrFrameId(JNI_DATA* jniData, int currFrameId);

        static void jSetPlayState(JNI_DATA* jniData, int newPlayState);

        static void jSetReadComplete(JNI_DATA* jniData);

        static int callback(const void *inputBuffer, void *outputBuffer,
                            unsigned long framesPerBuffer,
                            const PaStreamCallbackTimeInfo* timeInfo,
                            PaStreamCallbackFlags statusFlags,
                            void *userData );
};

#endif