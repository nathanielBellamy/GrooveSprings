import {Action} from "@ngrx/store";
import {Track} from "../../../models/tracks/track.model";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {initialPlaybackState, PlaybackState} from "./playback.state";
import {Playlist} from "../../../models/playlist/playlist.model";
import {PlaylistRepr} from "../../../models/playlist/playlist_repr.model";

export abstract class GsPlaybackAction {
  handle(state: PlaybackState): PlaybackState {
    throw new Error("Descendents of GsPlaybackAction must implement handle(state: PlaybackState): PlaybackeState")
  }
}

export class AddTrackToPlaylist implements Action {
  readonly type = PlaybackActionTypes.AddTrackToPlaylist

  constructor(public payload: Track) { }
}

export class SetPlaylistAsCurr implements Action { // awaited in playback.reducer.ts
  readonly type = PlaybackActionTypes.SetPlaylistAsCurr

  constructor(public payload: PlaylistRepr) { }
}

export class SetPlaylistAsCurrSuccess implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.SetPlaylistAsCurrSuccess

  constructor(public payload: Playlist) { }

  handle(state: PlaybackState): PlaybackState {
    return {
      ...state,
      playlist: {...this.payload}
    }
  }
}

export class SetPlaylistAsCurrFailure implements Action {
  readonly type = PlaybackActionTypes.SetPlaylistAsCurrFailure
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

export class SetCurrFileSuccess implements Action {
  readonly type = PlaybackActionTypes.SetCurrFileSuccess

  constructor(public initialLoad: boolean) { }
}

export class SetCurrFileFailure implements Action {
  readonly type = PlaybackActionTypes.SetCurrFileFailure

  constructor(e: any) {
    console.error(e)
  }
}

export class SetCurrPlaylistTrackIdx implements Action {
  readonly type = PlaybackActionTypes.SetCurrPlaylistTrackIdx

  constructor(public trackIdx: number) {}
}

export class SetCurrTrack implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.SetCurrTrack

  constructor(private track: Track, public initialLoad: boolean) {}

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

export class FetchAppState implements Action {
  readonly type = PlaybackActionTypes.FetchAppState
}

export class PlayTrig implements Action {
  readonly type = PlaybackActionTypes.PlayTrig
}

export class PauseTrig implements Action {
  readonly type = PlaybackActionTypes.PauseTrig
}

export class StopTrig implements Action {
  readonly type = PlaybackActionTypes.StopTrig
}

export class PlaybackSpeedTrig implements Action {
  readonly type = PlaybackActionTypes.PlaybackSpeedTrig

  constructor(public speed: number) { }
}

export class SetPlaybackState implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.SetPlaybackState

  constructor(public state: PlaybackState) { }

  public handle(_: PlaybackState): PlaybackState {
    return {...this.state}
  }
}

export class CachePlaybackState implements Action {
  readonly type = PlaybackActionTypes.CachePlaybackState
}

export class NextTrack implements Action {
  readonly type = PlaybackActionTypes.NextTrack

  public handle(state: PlaybackState): PlaybackState {
    const newPlaylistCurrTrackIndex: number = (state.currPlaylistTrackIdx + 1) % (state.playlist.tracks.length)
    return {
      ...state,
      currPlaylistTrackIdx: newPlaylistCurrTrackIndex,
    }
  }
}

export class HydrateAppState implements Action, GsPlaybackAction {
  readonly type = PlaybackActionTypes.HydrateAppState

  constructor(public payload: PlaybackState) { }

  public handle(_: PlaybackState): PlaybackState {
    return {...this.payload}
  }
}
