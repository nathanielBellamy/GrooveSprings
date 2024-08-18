#include "gs_effect_reverb.h"
#include "MVerb.h"
#include "../constans.h"

using namespace std;

void GS_EFFECT_REVERB::process() {
    mVerb.process(bufferIns, bufferOuts, AUDIO_BUFFER_FRAMES);
}

void GS_EFFECT_REVERB::setParameter(int index, float value)
{
	if (index < MVerb<float>::NUM_PARAMS && value <= 1. && value >= 0. )
    {
        mVerb.setParameter(index, parameters[index]);
    }
}