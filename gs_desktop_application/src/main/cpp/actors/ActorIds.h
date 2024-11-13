//
// Created by ns on 11/13/24.
//

#ifndef ACTOR_IDS_H
#define ACTOR_IDS_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "../atoms.h"

using namespace caf;

namespace Gs {
namespace Act {

    enum ActorIds {
      SUPERVISOR = 1,
      DISPLAY    = 2,
      PLAYBACK   = 3,
    };

}
}
#endif //ACTOR_IDS_H
