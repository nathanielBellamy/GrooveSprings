//
// Created by ns on 10/20/24.
//

#ifndef GSSUPERVISOR_H
#define GSSUPERVISOR_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_registry.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "./ActorIds.h"
#include "../messaging/atoms.h"
#include "./Display.h"
#include "./Playback.h"

using namespace caf;

namespace Gs {
namespace Act {

struct SupervisorTrait {

    using signatures = type_list<result<void>(strong_actor_ptr, init_display_ar, bool)>;

};

using Supervisor = typed_actor<SupervisorTrait>;

struct SupervisorState {
     bool running;
     bool display;
     strong_actor_ptr displayActorPtr;
     strong_actor_ptr playbackActorPtr;
     actor_system& sys;

     Supervisor::pointer self;

     SupervisorState(Supervisor::pointer self, actor_system& sys) :
         self(self)
       , sys(sys)
       , running(false)
       , display(false)
         {
           self->system().registry().put(ActorIds::SUPERVISOR, actor_cast<strong_actor_ptr>(self));
//           auto gs_app_state_manager = sys.spawn(actor_from_state<gs_app_state_manager_state>);
//           auto gs_controller = sys.spawn(actor_from_state<gs_controller_state
           auto display = sys.spawn(actor_from_state<DisplayState>, actor_cast<strong_actor_ptr>(self));
           displayActorPtr = actor_cast<strong_actor_ptr>(display);

           auto playback = sys.spawn(actor_from_state<PlaybackState>, actor_cast<strong_actor_ptr>(self));
           playbackActorPtr = actor_cast<strong_actor_ptr>(playback);

           self->anon_send(
               display,
               actor_cast<strong_actor_ptr>(self),
               init_display_a_v
           );
         }

     Supervisor::behavior_type make_behavior() {
       return {
           [this](strong_actor_ptr replyToPtr, init_display_ar, bool success) {
             this->display = success;
             if (init_success())
                 this->running = true;
             std::cout << "Supervisor display : " << this->display << std::endl;
             std::cout << "Supervisor running : " << this->running << std::endl;
             actor replyTo = actor_cast<actor>(replyToPtr);
           },
       };
     };

     bool init_success() {
         return this->display;
     };
};

} // Act
} // Gs

#endif //GSSUPERVISOR_H
