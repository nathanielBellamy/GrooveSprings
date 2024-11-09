//
// Created by ns on 11/4/24.
//

#ifndef DISPLAY_H
#define DISPLAY_H

#include <iostream>

#include "caf/actor_from_state.hpp"
#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

//#include <QApplication>
#include <QFrame>
#include <QMainWindow>
#include <QLabel>
#include <QScreen>

using namespace caf;

namespace Gs {
namespace Gui {

class MainWindow : public QMainWindow {
  public:
    int init(int argc, char *argv[]);

    MainWindow(actor_system& sys);

  private:
    QFrame frame;
    QLabel label {&frame};
};

} // Gui
} // Gs


#endif //DISPLAY_H
