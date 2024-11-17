//
// Created by ns on 11/16/24.
//

#ifndef APPSTATEMANAGER_H
#define APPSTATEMANAGER_H

//
// Created by ns on 11/11/24.
//

#include "caf/actor_ostream.hpp"
#include "caf/actor_registry.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

#include "./ActorIds.h"
#include "../messaging/atoms.h"
#include "../messaging/EnvelopeQtPtr.h"

#include "../gui/MainWindow.h"
#include "../enums/PlayStates.h"

#include "../AppState.h"

using namespace caf;
using namespace Gs::Gui;

namespace Gs {
namespace Act {

struct AppStateManagerTrait {

    using signatures = type_list<result<void>(EnvelopeQtPtr, tc_trig_play_a),
                                 result<void>(strong_actor_ptr, EnvelopeQtPtr, bool, tc_trig_play_ar)
                               >;

};

using AppStateManager = typed_actor<AppStateManagerTrait>;

struct AppStateManagerState {

     AppStateManager::pointer self;
     AppState appState = AppState(Gs::PlayStates::PLAY);
     actor playback;

     AppStateManagerState(AppStateManager::pointer self, strong_actor_ptr supervisor) :
          self(self)
        {
           self->link_to(supervisor);
           self->system().registry().put(ActorIds::APP_STATE_MANAGER, actor_cast<strong_actor_ptr>(self));

           playback = actor_cast<actor>(self->system().registry().get(ActorIds::PLAYBACK));

        }

     AppStateManager::behavior_type make_behavior() {
       return {
           [this](EnvelopeQtPtr mainWindowEnvelop, tc_trig_play_a) {
             std::cout << "AppStateManager : tc_trig_play_a" << std::endl;

             this->self->anon_send(
                 playback,
                 actor_cast<strong_actor_ptr>(self),
                 mainWindowEnvelop,
                 tc_trig_play_a_v
             );
           },
           [this](strong_actor_ptr, EnvelopeQtPtr mainWindowEnvelop, bool success, tc_trig_play_ar) {
             std::cout << "AppStateManager : tc_trig_play_ar" << std::endl;

             MainWindow* mainWindow = reinterpret_cast<MainWindow*>(mainWindowEnvelop.ptr);
             if (success) {
               appState = AppState::setPlayState(appState, Gs::PlayStates::PLAY);
               mainWindow->setPlayState(Gs::PlayStates::PLAY);
             } else {
               appState = AppState::setPlayState(appState, Gs::PlayStates::STOP);
               mainWindow->setPlayState(Gs::PlayStates::STOP);
             }
           },
       };
     };
};

} // Act
} // Gs

#endif //APPSTATEMANAGER_H
