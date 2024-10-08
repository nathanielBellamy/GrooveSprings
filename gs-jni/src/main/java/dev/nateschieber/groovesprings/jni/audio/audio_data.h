#ifndef AUDIO_DATA_H
#define AUDIO_DATA_H
#include <sndfile.hh>
#include "./jni_data.h"

struct AUDIO_DATA {
    sf_count_t  index;
    float       *buffer;
    SNDFILE     *file;
    SF_INFO     sfinfo;
    long        readcount;
    int         playState;
    float       playbackSpeed;
    bool        readComplete;
    float       volume;
    float       fadeIn;
    float       fadeOut;

    AUDIO_DATA(float* buffer, SNDFILE* file, SF_INFO sfinfo, sf_count_t index, long readcount, int playState) :
          buffer(buffer)
        , file(file)
        , sfinfo(sfinfo)
        , index(index)
        , readcount(readcount)
        , playState(playState)
        , readComplete(false)
        , volume(0.0)
        , fadeIn(1.0)
        , fadeOut(1.0) {}
};

#endif