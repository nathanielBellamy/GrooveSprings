import {initialLibraryState, LibraryState} from "./library.state";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "./library.actiontypes";
import {Artist} from "../../../models/artists/artist.model";
import {Album} from "../../../models/albums/album.model";
import {Playlist} from "../../../models/playlist/playlist.model";
import {PlaybackActionTypes} from "../../playback/store/playback.actiontypes";

export abstract class GsLibraryActionResult {
  public payload: any

  handle(state: LibraryState): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionResult")
  }
}

export class Identity implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.Identity

  public payload: null = null

  handle(state: LibraryState): LibraryState {
    return state;
  }
}

export class FetchAll implements Action {
  readonly type = LibraryActionTypes.FetchAll
}


export class SetArtistsFilter implements Action {
  readonly type = LibraryActionTypes.SetArtistsFilter

  constructor(public payload: Artist[]) {
  }
}

export class ClearArtistsFilter implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.ClearArtistsFilter

  public payload: null = null

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      filters: {
        ...state.filters,
        artists: []
      }
    }
  }
}

export class SetAlbumsFilter implements Action {
  readonly type = LibraryActionTypes.SetAlbumsFilter

  constructor(public payload: Album[]) {}
}

export class ClearAlbumsFilter implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.ClearAlbumsFilter

  public payload: null = null

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      filters: {
        ...state.filters,
        albums: []
      }
    }
  }
}

export class SetPlaylistsFilter implements Action {
  readonly type = LibraryActionTypes.SetPlaylistsFilter

  constructor(public playlists: Playlist[]) { }
}

export class LibraryScan implements Action {
  readonly type = LibraryActionTypes.LibraryScan
}

export class LibraryScanSuccess implements Action {
  readonly type = LibraryActionTypes.LibraryScanSuccess
}

export class LibraryScanFailure implements Action {
  readonly type = LibraryActionTypes.LibraryScanFailure

  constructor(e: any) {
    console.error(e)
  }
}

export class PlaylistCreate implements Action {
  readonly type = LibraryActionTypes.PlaylistCreate

  constructor(public playlist: Playlist) {
  }
}

export class ClearLibrary implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.ClearLibrary

  public payload: null = null

  handle(_: LibraryState): LibraryState {
    return initialLibraryState;
  }
}

export class ClearLibrarySuccess implements Action {
  readonly type = LibraryActionTypes.ClearLibrarySuccess
}

export class ClearLibraryFailure implements Action {
  readonly type = LibraryActionTypes.ClearLibraryFailure

  constructor(e: any) {
    console.error(e)
  }
}

export * from './actions/albums.actions'
export * from './actions/artists.actions'
export * from './actions/tracks.actions'
