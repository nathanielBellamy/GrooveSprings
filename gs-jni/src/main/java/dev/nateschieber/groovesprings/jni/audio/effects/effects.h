#ifndef EFFECTS_H
#define EFFECTS_H
#include "./MVerb.h"

struct EFFECTS {
    float           *bufferOut;
    MVerb<float>    mVerb;
    bool            mVerbBypass;
    float           **mVerbBufferIns;
    float           **mVerbBufferOuts;

    EFFECTS(float *bufferOut, float **mVerbBufferIns, float **mVerbBufferOuts) :
          bufferOut(bufferOut)
        , mVerbBufferIns(mVerbBufferIns)
        , mVerbBufferOuts(mVerbBufferOuts)
        , mVerb(MVerb<float>())
//        , mVerbBypass(true) {}
        , mVerbBypass(false) {}
};

#endif
