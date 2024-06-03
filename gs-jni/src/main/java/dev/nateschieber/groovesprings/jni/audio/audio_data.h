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
    JNI_DATA    jniData;

    AUDIO_DATA(float* buffer, SNDFILE* file, SF_INFO sfinfo, sf_count_t index, long readcount, JNI_DATA jniData) :
          buffer(buffer)
        , file(file)
        , sfinfo(sfinfo)
        , index(index)
        , jniData(jniData) {}
};

#endif