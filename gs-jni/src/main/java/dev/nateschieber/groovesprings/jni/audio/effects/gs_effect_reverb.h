#ifndef GS_EFFECT_REVERB_H
#define GS_EFFECT_REVERB_H
#include "MVerb.h"
#include "../constants.h"

class GS_EFFECT_REVERB {
    float**        bufferIns;
    float**        bufferOuts;
    MVerb<float>   mVerb;

    public:
        GS_EFFECT_REVERB (float** bufferIns, float** bufferOuts) :
              bufferIns(bufferIns)
            , bufferOuts(bufferOuts) {
                setParameter(MVerb<float>::DAMPINGFREQ, 0.);
                setParameter(MVerb<float>::DENSITY, 0.5);
                setParameter(MVerb<float>::BANDWIDTHFREQ, 1.);
                setParameter(MVerb<float>::DECAY, 0.5);
                setParameter(MVerb<float>::PREDELAY, 0.);
                setParameter(MVerb<float>::SIZE, 0.5);
                setParameter(MVerb<float>::GAIN, 1.);
                setParameter(MVerb<float>::MIX, 0.15);
                setParameter(MVerb<float>::EARLYMIX, 0.75);
            }

        void setParameter(int index, float value);
};

#endif
