import {Action} from "@ngrx/store";
import {Track} from "../../../models/tracks/track.model";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {initialPlaybackState, PlaybackState} from "./playback.state";

export abstract class GsPlaybackAction {
  handle(state: PlaybackState): PlaybackState {
    throw new Error("Descendents of GsPlaybackAction must implement handle(state: PlaybackState): PlaybackeState")
  }
}

export class AddTrackToPlaylist implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.AddTrackToPlaylist

  constructor(private payload: Track) {
  }

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      playlist: {
        ...state.playlist,
        tracks: state.playlist.tracks.concat([this.payload])
      }
    }
  }
}

export class ClearPlaylist implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.ClearPlaylist

  handle(_: PlaybackState): PlaybackState {
    return initialPlaybackState
  }
}

export class SetCurrPlaylistTrackIdx implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.SetCurrPlaylistTrackIdx

  constructor(private payload: number) {}

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      currPlaylistTrackIdx: this.payload
    }
  }
}
