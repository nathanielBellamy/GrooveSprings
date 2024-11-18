//
// Created by ns on 11/16/24.
//

#ifndef APPSTATE_H
#define APPSTATE_H

#include "./enums/PlayState.h"

class AppState {

  public:
    AppState(Gs::PlayState playState);

    static AppState setPlayState(AppState appState, Gs::PlayState playState);

  private:
    Gs::PlayState playState;
};

#endif //APPSTATE_H
