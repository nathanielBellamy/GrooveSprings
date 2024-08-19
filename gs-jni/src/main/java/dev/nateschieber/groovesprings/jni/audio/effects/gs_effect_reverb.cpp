#include "./gs_effect_reverb.h"
#include "./em_verb.h"
#include "../constants.h"

#include <string.h>

using namespace std;

GS_EFFECT_REVERB::GS_EFFECT_REVERB (float** bufferIns, float** bufferOuts) :
      bufferIns(bufferIns)
    , bufferOuts(bufferOuts)
    , mVerb(MVerb<float>()){
//        setParameter(MVerb<float>::DAMPINGFREQ, 0.);
//        setParameter(MVerb<float>::DENSITY, 0.5);
//        setParameter(MVerb<float>::BANDWIDTHFREQ, 1.);
//        setParameter(MVerb<float>::DECAY, 0.5);
//        setParameter(MVerb<float>::PREDELAY, 0.);
//        setParameter(MVerb<float>::SIZE, 0.5);
//        setParameter(MVerb<float>::GAIN, 1.);
//        setParameter(MVerb<float>::MIX, 0.15);
//        setParameter(MVerb<float>::EARLYMIX, 0.75);
    }

void GS_EFFECT_REVERB::process() {
//    mVerb.process(bufferIns, bufferOuts, AUDIO_BUFFER_FRAMES);
}

void GS_EFFECT_REVERB::setParameter(int index, float value)
{
//	if (index < MVerb<float>::NUM_PARAMS && value <= 1. && value >= 0. )
//    {
//        mVerb.setParameter(index, value);
//    }
}