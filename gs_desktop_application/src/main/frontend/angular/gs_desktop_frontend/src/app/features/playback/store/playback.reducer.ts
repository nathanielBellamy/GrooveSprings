import {initialPlaybackState, PlaybackState} from "./playback.state";
import {Action} from "@ngrx/store";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {
  HydrateAppState
} from "./playback.actions";


export function playbackReducer(state = initialPlaybackState, action: Action): PlaybackState {
  var actionT: any = null
  switch (action.type) {
    // The server pushes all PlaybackState updates through the gs-display websocket
    case PlaybackActionTypes.HydrateAppState:
      actionT = action as HydrateAppState
      return actionT.handle(state)

    default:
      return state
  }
}
