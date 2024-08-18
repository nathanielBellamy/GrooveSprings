#ifndef GS_EFFECTS_H
#define GS_EFFECTS_H
#include "../constants.h"
#include "gs_effect_reverb.h"

struct GS_EFFECTS {
    GS_EFFECT_REVERB reverb;

    GS_EFFECTS (float** mVerbBufferIns, float** mVerbBufferOuts) :
          reverb(
            GS_EFFECT_REVERB(mVerbBufferIns, mVerbBufferOuts)
          ) {}
};

#endif
