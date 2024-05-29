import {initialPlaybackState, PlaybackState} from "./playback.state";
import {Action} from "@ngrx/store";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {AddTrackToPlaylist, SetCurrPlaylistTrackIdx} from "./playback.actions";


export function playbackReducer(state = initialPlaybackState, action: Action): PlaybackState {
  var actionT: any = null
  switch (action.type) {
    case PlaybackActionTypes.AddTrackToPlaylist:
      actionT = action as AddTrackToPlaylist
      return actionT.handle(state)

    case PlaybackActionTypes.SetCurrPlaylistTrackIdx:
      actionT = action as SetCurrPlaylistTrackIdx
      return actionT.handle(state)

    default:
      return state
  }
}
