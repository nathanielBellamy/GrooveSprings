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

    AUDIO_DATA(float* buffer, SNDFILE* file, SF_INFO sfinfo, sf_count_t index, long readcount) :
          buffer(buffer)
        , file(file)
        , sfinfo(sfinfo)
        , index(index) {}
};

#endif