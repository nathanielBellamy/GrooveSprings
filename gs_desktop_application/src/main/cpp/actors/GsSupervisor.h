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

    using signatures = type_list<result<void>(init_check_a)>;

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
           auto gs_display = sys.spawn(actor_from_state<gs_display_state>, actor_cast<strong_actor_ptr>(self));
           self->mail(init_display_a{})
               .request(gs_display, infinite)
               .then([&](bool success) {
                     display = success;
                     std::cout << "gs_supervisor_state::display = " << display << std::endl;
                 });

//           auto gs_app_state_manager = sys.spawn(actor_from_state<gs_app_state_manager_state>);
//           auto gs_controller = sys.spawn(actor_from_state<gs_controller_state
         }

     gs_supervisor::behavior_type make_behavior() {
       return {
           [this](init_check_a) {
             if (this->init_success()) {
               this->running = true;
             } else {
               this->running = false;
             }
             std::cout << "gs_supervisor display: " << this->display << std::endl;
             std::cout << "gs_supervisor running: " << this->running << std::endl;
           },
       };
     };

     bool init_success() {
         return this->display;
     };
};

#endif //GSSUPERVISOR_H
