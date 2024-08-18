#ifndef GS_EFFECT_REVERB_H
#define GS_EFFECT_REVERB_H
#include "./MVerb.h"
#include "../constants.h"
#include<cmath>
#include <string.h>

class GS_EFFECT_REVERB {
    float**        bufferIns;
    float**        bufferOuts;
//    MVerb<float>   mVerb;

    public:
        GS_EFFECT_REVERB(float** bufferIns, float** bufferOUts);
        void setParameter(int index, float value);
        void process();
};

#endif
