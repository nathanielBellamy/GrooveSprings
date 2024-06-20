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

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      playlist: initialPlaybackState.playlist,
      currPlaylistTrackIdx: initialPlaybackState.currPlaylistTrackIdx
    }
  }
}

export class SetCurrFile implements Action {
  readonly type = PlaybackActionTypes.SetCurrFile
}

export class SetCurrFileSuccess implements Action {
  readonly type = PlaybackActionTypes.SetCurrFileSuccess
}

export class SetCurrFileFailure implements Action {
  readonly type = PlaybackActionTypes.SetCurrFileFailure

  constructor(e: any) {
    console.error(e)
  }
}

export class SetCurrPlaylistTrackIdx implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.SetCurrPlaylistTrackIdx

  constructor(private trackIdx: number, private track: Track) {}

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      currPlaylistTrackIdx: this.trackIdx
    }
  }

  getTrack(): Track {
    return this.track
  }
}

export class SetCurrTrack implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.SetCurrTrack

  constructor(private track: Track) {}

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      currTrack: {...this.track}
    }
  }

  getTrack(): Track {
    return this.track
  }
}

export class UpdateCurrPlaylistTrackidx implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.UpdateCurrPlaylistTrackIdx

  constructor(private byVal: number) { }

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      currPlaylistTrackIdx: (state.currPlaylistTrackIdx + this.byVal) % state.playlist.tracks.length
    }
  }
}
