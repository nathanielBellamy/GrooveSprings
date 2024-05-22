#ifndef AUDIO_H
#define AUDIO_H

#include "./audio_data.h"

class Audio
{
        AUDIO_DATA data;

    public:
        Audio(char const* file);
};

#endif