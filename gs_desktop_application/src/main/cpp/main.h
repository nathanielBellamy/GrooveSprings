
#ifndef MAIN_H
#define MAIN_H

#include <iostream>
#include <chrono>
#include <string>
#include <thread>

#include "caf/actor_from_state.hpp"
#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "./atoms.h"
#include "./actors/GsSupervisor.h"
#include "./qt/display.h"

#include "./audio/effects/vst3/host/audiohost/source/audiohost.h"
#include "./audio/effects/vst3/host/hostclasses.hpp"
#include "./audio/effects/vst3/host/editorhost/source/editorhost.h"

namespace GrooveSprings {

extern "C" {
    int main(int argc, char *argv[]);
}

} // GrooveSprings

#endif //MAIN_H
