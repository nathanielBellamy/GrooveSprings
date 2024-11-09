//
// Created by ns on 11/4/24.
//

#ifndef DISPLAY_H
#define DISPLAY_H

//#include <QApplication>
#include <QFrame>
#include <QMainWindow>
#include <QLabel>
#include <QScreen>

namespace Gs {
namespace Gui {

class MainWindow : public QMainWindow {
  public:
    int init(int argc, char *argv[]);

    MainWindow();

  private:
    QFrame frame;
    QLabel label {&frame};
};

} // Gui
} // Gs


#endif //DISPLAY_H
