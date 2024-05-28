import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {AlbumsData} from "../../../models/albums/albums_data.model";
import {GsLibraryActionFailure, GsLibraryActionSuccess} from "../library.actions";

export class FetchAlbums implements Action {
  readonly type = LibraryActionTypes.FetchAlbums
}

export class FetchAlbumsSuccess implements Action, GsLibraryActionSuccess {
  readonly type = LibraryActionTypes.FetchAlbumsSuccess

  constructor(public payload: AlbumsData) {
  }

  handle(state: LibraryState) {
    return {
      ...state,
      albumCount: this.payload.count,
      albums: this.payload.albums
    }
  }
}

export class FetchAlbumsFailure implements Action, GsLibraryActionFailure {
  readonly type = LibraryActionTypes.FetchAlbumsFailure

  constructor(public payload: any) {
  }

  handle(state: LibraryState) {
    console.error('GsLibraryActionFailure ## FetchAlbumsFailure')
    console.error(this.payload)
    return state
  }
}
