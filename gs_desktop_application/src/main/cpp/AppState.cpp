//
// Created by ns on 11/16/24.
//

#include "AppState.h"

AppState::AppState(Gs::PlayStates playState)
    : playState(playState)
      {}

AppState AppState::setPlayState(AppState appState, Gs::PlayStates playState) {
  AppState newState { playState };
  return newState;
};