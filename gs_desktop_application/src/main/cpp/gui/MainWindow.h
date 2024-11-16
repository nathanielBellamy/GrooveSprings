//
// Created by ns on 11/4/24.
//

#ifndef DISPLAY_H
#define DISPLAY_H

#include <iostream>

#include "caf/actor_from_state.hpp"
#include "caf/actor_ostream.hpp"
#include "caf/actor_registry.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"
#include "caf/scoped_actor.hpp"

#include "../actors/ActorIds.h"
#include "../messaging/atoms.h"
#include "../messaging/Envelope.h"
#include "../enums/PlayStates.h"

#include <QFrame>
#include <QMainWindow>
#include <QLabel>
#include <QScreen>
#include <QToolbar>
#include <QToolButton>
#include <QAction>
#include <QStyle>

using namespace caf;

namespace Gs {
namespace Gui {

class MainWindow : public QMainWindow {
  public:
    MainWindow(actor_system& sys);
    void setPlayState(Gs::PlayStates state);


  private:
    QFrame frame;
    QLabel label {&frame};
    QToolBar transportControl {&frame};
    QAction playTrigAction {style()->standardIcon(QStyle::StandardPixmap::SP_FileIcon), "&PlayTrig", &transportControl};
    actor_system& sys;
    actor displayActor;
    actor playbackActor;
    Gs::PlayStates playState;
};

} // Gui
} // Gs



#endif //DISPLAY_H
