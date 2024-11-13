//
// Created by ns on 11/3/24.
//

#ifndef GSDISPLAY_H
#define GSDISPLAY_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_registry.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "./ActorIds.h"
#include "../atoms.h"

using namespace caf;

namespace Gs {
namespace Act {

struct DisplayTrait {

    using signatures = type_list<result<void>(strong_actor_ptr /*replyTo*/, init_display_a),
                                 result<void>(strong_actor_ptr /*replyTo*/, tc_trig_play_ar)
                               >;

};

using Display = typed_actor<DisplayTrait>;

struct DisplayState {

     Display::pointer self;

     DisplayState(Display::pointer self, strong_actor_ptr supervisor) :
          self(self)
        {
           self->link_to(supervisor);
           self->system().registry().put(ActorIds::DISPLAY, actor_cast<strong_actor_ptr>(self));
        }

     Display::behavior_type make_behavior() {
       return {
           [this](strong_actor_ptr reply_to, init_display_a) {
             std::cout << "Display : init_display_a" << std::endl;

             actor reply_to_actor = actor_cast<actor>(reply_to);
             this->self->anon_send(
                 reply_to_actor,
                 actor_cast<strong_actor_ptr>(self),
                 init_display_ar_v,
                 true
             );
           },
           [this](strong_actor_ptr reply_to, tc_trig_play_ar) {
             std::cout << "Display  : tc_trig_play_ar" << std::endl;
           },
       };
     };
};

} // Act
} // Gs


#endif //GSDISPLAY_H
