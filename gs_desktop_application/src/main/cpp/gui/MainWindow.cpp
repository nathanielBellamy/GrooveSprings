//
// Created by ns on 11/4/24.
//

#include "./MainWindow.h"

using namespace caf;

namespace Gs {
namespace Gui {

MainWindow::MainWindow(actor_system& sys)
    : sys(sys)
    {
  std::cout << "MainWindow detached_actors: " << sys.detached_actors() << std::endl;

  label.setText("Welcome to\nGrooveSprings.");
  label.setFont({label.font().family(), 36});
//  label.resize(label.sizeHint());

  transportControl.addAction(&playTrigAction);
  transportControl.addSeparator();
  transportControl.setToolButtonStyle(Qt::ToolButtonTextBesideIcon);

  connect(&playTrigAction, &QAction::triggered, [&] {
    strong_actor_ptr displayPtr = sys.registry().get(Gs::Act::ActorIds::DISPLAY);
    strong_actor_ptr playbackPtr = sys.registry().get(Gs::Act::ActorIds::PLAYBACK);

    scoped_actor self{sys};
    self->anon_send(
        actor_cast<actor>(playbackPtr),
        displayPtr,
        tc_trig_play_a_v
    );
    std::cout << "PlayTrig" << std::endl;

  });

  addToolBar(Qt::BottomToolBarArea, &transportControl);
  setCentralWidget(&frame);
  setUnifiedTitleAndToolBarOnMac(true);
  setWindowTitle("GrooveSprings");
//  resize(label.sizeHint());
  resize(640, 480);
}

} // Gui
} // Gs