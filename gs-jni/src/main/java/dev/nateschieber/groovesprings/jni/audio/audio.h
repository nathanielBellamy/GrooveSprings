#ifndef AUDIO_H
#define AUDIO_H

#include <sndfile.hh>
#include <portaudio.h>
#include "./audio_data.h"


class Audio
{
        AUDIO_DATA data;
        char const* file;

    public:
        Audio(char const* file);

        int run();

        int init_pa(AUDIO_DATA *audioData);

        void freeAudioData(AUDIO_DATA *audioData);

        static int callback(const void *inputBuffer, void *outputBuffer,
                            unsigned long framesPerBuffer,
                            const PaStreamCallbackTimeInfo* timeInfo,
                            PaStreamCallbackFlags statusFlags,
                            void *userData );
};

#endif