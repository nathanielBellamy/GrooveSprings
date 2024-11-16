//
// Created by ns on 11/11/24.
//

#ifndef PLAYBACK_H
#define PLAYBACK_H

#include "caf/actor_ostream.hpp"
#include "caf/actor_registry.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "./ActorIds.h"
#include "../messaging/atoms.h"
#include "../messaging/Envelope.h"

using namespace caf;

namespace Gs {
namespace Act {

struct PlaybackTrait {

    using signatures = type_list<result<void>(strong_actor_ptr, Envelope, tc_trig_play_a)>;

};

using Playback = typed_actor<PlaybackTrait>;

struct PlaybackState {

     Playback::pointer self;

     PlaybackState(Playback::pointer self, strong_actor_ptr supervisor) :
          self(self)
        {
           self->link_to(supervisor);
           self->system().registry().put(ActorIds::PLAYBACK, actor_cast<strong_actor_ptr>(self));
        }

     Playback::behavior_type make_behavior() {
       return {
           [this](strong_actor_ptr reply_to, Envelope mainWindowEnvelop, tc_trig_play_a) {
             std::cout << "Playback : tc_trig_play_a" << std::endl;

             actor replyToActor = actor_cast<actor>(reply_to);
             this->self->anon_send(
                 replyToActor,
                 actor_cast<strong_actor_ptr>(self),
                 mainWindowEnvelop,
                 tc_trig_play_ar_v
             );
           },
       };
     };
};

} // Act
} // Gs

#endif //PLAYBACK_H
