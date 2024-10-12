#ifndef AUDIO_H
#define AUDIO_H

#include <sndfile.hh>
#include <portaudio.h>
#include "caf/actor_system.hpp"
#include "./audio_data.h"
#include "./caf_data.h"
#include "./effects/vst3/host/audiohost/source/audiohost.h"

class Audio
{
        caf::actor_system* cafEnv;
        long threadId;
        char const* fileName;
        long initialFrameId;
        long vst3HostPtr;

      public:
        Audio(caf::actor_system* env, long threadId, const char* fileName, long initialFrameId, long vst3HostPtr);

        int run();

        void freeAudioData(AUDIO_DATA *audioData);

        static void jSetCurrFrameId(CAF_DATA* cafData, int currFrameId);

        static void jSetPlayState(CAF_DATA* cafData, int newPlayState);

        static void jSetReadComplete(CAF_DATA* cafData);

        static int callback(const void *inputBuffer, void *outputBuffer,
                            unsigned long framesPerBuffer,
                            const PaStreamCallbackTimeInfo* timeInfo,
                            PaStreamCallbackFlags statusFlags,
                            void *userData );
};

#endif