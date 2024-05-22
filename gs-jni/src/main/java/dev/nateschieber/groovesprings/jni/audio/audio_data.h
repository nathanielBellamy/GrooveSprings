#ifndef AUDIO_DATA_H
#define AUDIO_DATA_H
#include <sndfile.hh>

typedef struct {
    SNDFILE *file;
    SF_INFO sfinfo;
} AUDIO_DATA;

#endif