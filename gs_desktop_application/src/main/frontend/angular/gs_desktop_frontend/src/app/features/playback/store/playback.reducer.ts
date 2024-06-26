import {initialPlaybackState, PlaybackState} from "./playback.state";
import {Action} from "@ngrx/store";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {
  AddTrackToPlaylist,
  ClearPlaylist,
  SetCurrPlaylistTrackIdx,
  SetCurrTrack,
  SetPlaylistAsCurr, SetPlaylistAsCurrSuccess
} from "./playback.actions";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {PlaylistUpdate} from "../../library/store/library.actions";


export function playbackReducer(state = initialPlaybackState, action: Action): PlaybackState {
  var actionT: any = null
  switch (action.type) {
    case PlaybackActionTypes.AddTrackToPlaylist:
      actionT = action as AddTrackToPlaylist
      return actionT.handle(state)

    case PlaybackActionTypes.ClearPlaylist:
      actionT = action as ClearPlaylist
      return actionT.handle(state)

    case PlaybackActionTypes.SetCurrPlaylistTrackIdx:
      actionT = action as SetCurrPlaylistTrackIdx
      return actionT.handle(state)

    case PlaybackActionTypes.SetCurrTrack:
      actionT = action as SetCurrTrack
      return actionT.handle(state)

    case PlaybackActionTypes.SetPlaylistAsCurrSuccess:
      actionT = action as SetPlaylistAsCurrSuccess
      return actionT.handle(state)

    case LibraryActionTypes.PlaylistUpdate:
      actionT = action as PlaylistUpdate
      return actionT.handle(state)

    default:
      return state
  }
}
