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

// TODO: rename + namespace
class display : public QMainWindow {
  public:
    int init(int argc, char *argv[]);

    display() {
      label.setText("\U0001F44B, \U0001F30E\U00002757");
      label.setFont({label.font().family(), 72});
      label.resize(label.sizeHint());

      setCentralWidget(&frame);
      setWindowTitle("Hello gs world (emoticons)");
      resize(label.sizeHint());
    }

  private:
    QFrame frame;
    QLabel label {&frame};
};



#endif //DISPLAY_H
