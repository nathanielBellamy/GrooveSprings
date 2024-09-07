#ifndef AUDIO_DATA_H
#define AUDIO_DATA_H
#include <sndfile.hh>
#include "./jni_data.h"
#include "./effects/vst3/host/audiohost/source/audiohost.h"

struct AUDIO_DATA {
    sf_count_t                      index;
    float                           *buffer;
    SNDFILE                         *file;
    SF_INFO                         sfinfo;
    long                            readcount;
    int                             playState;
    float                           playbackSpeed;
    bool                            readComplete;
    float                           volume;
    float                           fadeIn;
    float                           fadeOut;
    Steinberg::Vst::AudioClientPtr  vst3Processor;

    AUDIO_DATA(float* buffer, SNDFILE* file, SF_INFO sfinfo, sf_count_t index, long readcount, int playState, Steinberg::Vst::AudioClientPtr vst3Processor) :
          buffer(buffer)
        , file(file)
        , sfinfo(sfinfo)
        , index(index)
        , readcount(readcount)
        , playState(playState)
        , readComplete(false)
        , volume(0.0)
        , fadeIn(1.0)
        , fadeOut(1.0)
        , vst3Processor(vst3Processor)
        {}
};

#endif