//
// Created by ns on 11/16/24.
//

#ifndef APPSTATE_H
#define APPSTATE_H

#include "./enums/PlayStates.h"

class AppState {

  public:
    AppState(Gs::PlayStates playState);

    static AppState setPlayState(AppState appState, Gs::PlayStates playState);

  private:
    Gs::PlayStates playState;
};

#endif //APPSTATE_H
