import {LibraryActionTypes} from "../library.actiontypes";
import {Action} from "@ngrx/store";
import {GsLibraryActionResult} from "../library.actions";
import {PlaylistsData} from "../../../../models/playlist/playlists_data.model";
import {LibraryState} from "../library.state";


export class FetchPlaylists implements Action {
  public readonly type = LibraryActionTypes.FetchPlaylists
}

export class FetchPlaylistsSuccess implements Action, GsLibraryActionResult {
  public readonly type = LibraryActionTypes.FetchPlaylistsSuccess

  constructor(public payload: PlaylistsData) { }

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      playlistCount: this.payload.count,
      playlists: this.payload.playlists
    }
  }
}

export class FetchPlaylistsFailure implements Action, GsLibraryActionResult {
  public readonly type = LibraryActionTypes.FetchPlaylistsFailure

  constructor(public payload: any) { }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## FetchPlaylistsFailure')
    console.error(this.payload)
    return {...state}
  }
}
