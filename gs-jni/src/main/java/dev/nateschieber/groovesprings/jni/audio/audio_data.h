#ifndef AUDIO_DATA_H
#define AUDIO_DATA_H
#include <sndfile.hh>
#include "./jni_data.h"

typedef struct {
    float       *buffer;
    SNDFILE     *file;
    SF_INFO     sfinfo;
    sf_count_t  index;
} AUDIO_DATA;

#endif