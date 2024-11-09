//
// Created by ns on 11/4/24.
//

#include <QApplication>
#include "./MainWindow.h"

#include "caf/actor_from_state.hpp"
#include "caf/actor_ostream.hpp"
#include "caf/actor_system.hpp"
#include "caf/caf_main.hpp"
#include "caf/event_based_actor.hpp"

using namespace caf;

namespace Gs {
namespace Gui {

MainWindow::MainWindow(actor_system& sys) {
  std::cout << "MainWindow detached_actors: " << sys.detached_actors() << std::endl;

  label.setText("\U0001F44B, \U0001F30E\U00002757");
  label.setFont({label.font().family(), 72});
  label.resize(label.sizeHint());
  setCentralWidget(&frame);
  setWindowTitle("Hello gs world (emoticons)");
  resize(label.sizeHint());
}

} // Gui
} // Gs