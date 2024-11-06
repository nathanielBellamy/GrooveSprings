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

    using signatures = type_list<result<void>(strong_actor_ptr, init_display_a)>;

};

using gs_display = typed_actor<gs_display_trait>;

struct gs_display_state {

     gs_display::pointer self;

     gs_display_state(gs_display::pointer self, strong_actor_ptr supervisor) :
          self(self)
        {
           self->link_to(supervisor);
        }

     gs_display::behavior_type make_behavior() {
       return {
           [this](strong_actor_ptr reply_to, init_display_a) {
             std::cout << "gs_display : init_display_a" << std::endl;

             actor reply_to_actor = actor_cast<actor>(reply_to);
             this->self->anon_send(
                 reply_to_actor,
                 actor_cast<strong_actor_ptr>(self),
                 init_display_ar{},
                 true
             );
           },
       };
     };
};


#endif //GSDISPLAY_H
