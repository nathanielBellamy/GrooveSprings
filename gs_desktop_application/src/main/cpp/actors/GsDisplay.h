//
// Created by ns on 11/3/24.
//

#ifndef GSDISPLAY_H
#define GSDISPLAY_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "../atoms.h"

using namespace caf;

struct gs_display_trait {

    using signatures = type_list<result<bool>(init_display_a)>;

};

using gs_display = typed_actor<gs_display_trait>;

struct gs_display_state {
     gs_display::pointer self;

     gs_display_state(gs_display::pointer self) :
          self(self)
        {}

     gs_display::behavior_type make_behavior() {
       return {
           [this](init_display_a) {
             std::cout << "gs_display : init_display_a" << std::endl;
             return true;
           },
       };
     };
};


#endif //GSDISPLAY_H
