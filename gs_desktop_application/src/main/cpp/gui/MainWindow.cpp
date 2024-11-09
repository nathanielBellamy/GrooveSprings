//
// Created by ns on 11/4/24.
//

#include <QApplication>
#include "./MainWindow.h"

namespace Gs {
namespace Gui {

MainWindow::MainWindow() {
      label.setText("\U0001F44B, \U0001F30E\U00002757");
      label.setFont({label.font().family(), 72});
      label.resize(label.sizeHint());

      setCentralWidget(&frame);
      setWindowTitle("Hello gs world (emoticons)");
      resize(label.sizeHint());
}

} // Gui
} // Gs