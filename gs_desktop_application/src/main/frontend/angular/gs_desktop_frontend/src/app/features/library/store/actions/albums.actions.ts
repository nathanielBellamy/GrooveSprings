import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionResult} from "../library.actions";
import {AlbumsData} from "../../../../models/albums/albums_data.model";
import {AlbumsByArtistIds} from "../../../../models/albums/albums_by_artist_ids.model";

export class FetchAlbums implements Action {
  readonly type = LibraryActionTypes.FetchAlbums
}

export class FetchAlbumsSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.FetchAlbumsSuccess

  constructor(public payload: AlbumsData) { }

  handle(state: LibraryState) {
    return {
      ...state,
      albumCount: this.payload.count,
      albums: this.payload.albums
    }
  }
}

export class FetchAlbumsFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.FetchAlbumsFailure

  constructor(public payload: any) {
  }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## FetchAlbumsFailure')
    console.error(this.payload)
    return state
  }
}

export class SetArtistsFilterAlbumsSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetArtistsFilterAlbumsSuccess

  constructor(public payload: AlbumsByArtistIds) {}

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      albumCount: this.payload.count,
      albums: this.payload.albums,
      filters: {
        ...state.filters,
        artists: state.artists.filter(artist => this.payload.artistIds.includes(artist.id)),
      }
    }
  }
}

export class SetArtistsFilterAlbumsFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetArtistsFilterAlbumsFailure

  constructor(public payload: any) {}

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## SetArtistsFilterAlbumsFailure')
    console.error(this.payload)
    return state
  }
}
