#ifndef EFFECTS_H
#define EFFECTS_H
#include "./MVerb.h"

struct EFFECTS {
    MVerb<float>    mVerb;
    bool            mVerbBypass;
    float           **mVerbBufferIns;
    float           **mVerbBufferOuts;

    EFFECTS(float **mVerbBufferIns, float **mVerbBufferOuts) :
          mVerbBufferIns(mVerbBufferIns)
        , mVerbBufferOuts(mVerbBufferOuts)
        , file(MVerb<float>())
        , mVerbBypass(true) {}
};

#endif
