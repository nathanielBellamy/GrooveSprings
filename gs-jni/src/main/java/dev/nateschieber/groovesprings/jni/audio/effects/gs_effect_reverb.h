#ifndef GS_EFFECT_REVERB_H
#define GS_EFFECT_REVERB_H
#include "./em_verb.h"
#include "../constants.h"
#include<cmath>
#include <string.h>

class GS_EFFECT_REVERB {
    public:
        float**        bufferIns;
        float**        bufferOuts;

        GS_EFFECT_REVERB(float** bufferIns, float** bufferOUts);
        void setParameter(int index, float value);
        void process();

    private:
        MVerb<float>   mVerb;
};

#endif
