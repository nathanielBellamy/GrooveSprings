//
// Created by ns on 10/20/24.
//

#ifndef GSSUPERVISOR_H
#define GSSUPERVISOR_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "../atoms.h"
#include "./GsDisplay.h"

using namespace caf;

struct gs_supervisor_trait {

    using signatures = type_list<result<void>(strong_actor_ptr, init_display_ar, bool)>;

};

using gs_supervisor = typed_actor<gs_supervisor_trait>;

struct gs_supervisor_state {
     bool running;
     bool display;
     actor_system& sys;

     gs_supervisor::pointer self;

     gs_supervisor_state(gs_supervisor::pointer self, actor_system& sys) :
         self(self)
       , sys(sys)
       , running(false)
       , display(false)
         {
//           auto gs_playback = sys.spawn(actor_from_state<gs_playback_state>);
//           auto gs_app_state_manager = sys.spawn(actor_from_state<gs_app_state_manager_state>);
//           auto gs_controller = sys.spawn(actor_from_state<gs_controller_state
           auto gs_display = sys.spawn(actor_from_state<gs_display_state>, actor_cast<strong_actor_ptr>(self));
           self->anon_send(
               gs_display,
               actor_cast<strong_actor_ptr>(self),
               init_display_a{}
           );
         }

     gs_supervisor::behavior_type make_behavior() {
       return {
           [this](strong_actor_ptr, init_display_ar, bool success) {
             this->display = success;
             if (init_success())
                 this->running = true;
             std::cout << "gs_supervisor display : " << this->display << std::endl;
             std::cout << "gs_supervisor running : " << this->running << std::endl;
           },
       };
     };

     bool init_success() {
         return this->display;
     };
};

#endif //GSSUPERVISOR_H
