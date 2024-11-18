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
#include "../enums/PlayState.h"

#include "../AppState.h"

using namespace caf;
using namespace Gs::Gui;

namespace Gs {
namespace Act {

struct AppStateManagerTrait {

    using signatures = type_list<
                                 result<void>(EnvelopeQtPtr, int /* Gs::PlayState */, tc_trig_a),
                                 result<void>(strong_actor_ptr, EnvelopeQtPtr, int /* Gs::PlayState */, bool, tc_trig_ar)
                               >;

};

using AppStateManager = typed_actor<AppStateManagerTrait>;

struct AppStateManagerState {

     AppStateManager::pointer self;
     AppState appState = AppState(Gs::PlayState::PLAY);
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
           [this](EnvelopeQtPtr mainWindowEnvelop, int playStateInt, tc_trig_a) {
             std::cout << "AppStateManager : tc_trig_a : " << playStateInt << std::endl;

             this->self->anon_send(
                 playback,
                 actor_cast<strong_actor_ptr>(self),
                 mainWindowEnvelop,
                 playStateInt,
                 tc_trig_a_v
             );
           },
           [this](strong_actor_ptr, EnvelopeQtPtr mainWindowEnvelop, int playStateInt, bool success, tc_trig_ar) {
             std::cout << "AppStateManager : tc_trig_ar : " << playStateInt << std::endl;
             Gs::PlayState playState = Gs::intToPs(playStateInt);

             MainWindow* mainWindow = reinterpret_cast<MainWindow*>(mainWindowEnvelop.ptr);
             if (!success) {
               appState = AppState::setPlayState(appState, Gs::PlayState::STOP);
               mainWindow->setPlayState(Gs::PlayState::STOP);
             }

             switch (playState) {
               case Gs::PlayState::PLAY:
                 appState = AppState::setPlayState(appState, Gs::PlayState::PLAY);
                 mainWindow->setPlayState(Gs::PlayState::PLAY);
                 break;
               case Gs::PlayState::PAUSE:
                 appState = AppState::setPlayState(appState, Gs::PlayState::PAUSE);
                 mainWindow->setPlayState(Gs::PlayState::PAUSE);
                 break;
               case Gs::PlayState::STOP:
                 appState = AppState::setPlayState(appState, Gs::PlayState::STOP);
                 mainWindow->setPlayState(Gs::PlayState::STOP);
                 break;
               case Gs::PlayState::RW:
                 appState = AppState::setPlayState(appState, Gs::PlayState::RW);
                 mainWindow->setPlayState(Gs::PlayState::RW);
                 break;
               case Gs::PlayState::FF:
                 appState = AppState::setPlayState(appState, Gs::PlayState::FF);
                 mainWindow->setPlayState(Gs::PlayState::FF);
                 break;
               default:
                 break;
             }
           },
       };
     };
};

} // Act
} // Gs

#endif //APPSTATEMANAGER_H
