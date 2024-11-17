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
#include "../messaging/atoms.h"
#include "../messaging/EnvelopeQtPtr.h"
#include "../enums/PlayStates.h"

#include "../gui/MainWindow.h"

using namespace caf;
using namespace Gs::Gui;

namespace Gs {
namespace Act {

struct DisplayTrait {

    using signatures = type_list<result<void>(strong_actor_ptr /*replyTo*/, init_display_a),
                                 result<void>(strong_actor_ptr /*replyTo*/, EnvelopeQtPtr, tc_trig_play_ar)
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
           [this](strong_actor_ptr reply_to, EnvelopeQtPtr mainWindowEnvelope, tc_trig_play_ar) {
             std::cout << "Display  : tc_trig_play_ar" << std::endl;
             MainWindow* mainWindow = reinterpret_cast<MainWindow*>(mainWindowEnvelope.ptr);
             mainWindow->setPlayState(Gs::PlayStates::PLAY);
           },
       };
     };
};

} // Act
} // Gs


#endif //GSDISPLAY_H
