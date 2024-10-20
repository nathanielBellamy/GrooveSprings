#ifndef AUDIO_H
#define AUDIO_H

#include <sndfile.hh>
#include <portaudio.h>
#include "jni.h"
#include "./audio_data.h"
#include "./jni_data.h"
#include "./effects/vst3/host/audiohost/source/audiohost.h"

class Audio
{
        JNIEnv* jniEnv;
        jlong threadId;
        char const* fileName;
        jlong initialFrameId;
        jlong vst3HostPtr;

      public:
        Audio(JNIEnv* env, jlong threadId, jstring jFileName, jlong initialFrameId, jlong vst3HostPtr);

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