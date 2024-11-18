//
// Created by ns on 11/16/24.
//

#include "AppState.h"

AppState::AppState(Gs::PlayState playState)
    : playState(playState)
      {}

AppState AppState::setPlayState(AppState appState, Gs::PlayState playState) {
  AppState newState { playState };
  return newState;
};