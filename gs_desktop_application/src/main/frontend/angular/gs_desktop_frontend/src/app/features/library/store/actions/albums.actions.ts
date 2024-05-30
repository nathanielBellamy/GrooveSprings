import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionFailure, GsLibraryActionSuccess} from "../library.actions";
import {AlbumsData} from "../../../../models/albums/albums_data.model";
import {AlbumsGetByArtistIds} from "../../../../models/albums/albums_get_by_artist_ids.model";
import {AlbumsByArtistIds} from "../../../../models/albums/albums_by_artist_ids.model";

export class FetchAlbums implements Action {
  readonly type = LibraryActionTypes.FetchAlbums
}

export class FetchAlbumsSuccess implements Action, GsLibraryActionSuccess {
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

export class FetchAlbumsFailure implements Action, GsLibraryActionFailure {
  readonly type = LibraryActionTypes.FetchAlbumsFailure

  constructor(public payload: any) {
  }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## FetchAlbumsFailure')
    console.error(this.payload)
    return state
  }
}

export class SetArtistsFilterAlbumsSuccess implements Action, GsLibraryActionSuccess {
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

export class SetArtistsFilterAlbumsFailure implements Action, GsLibraryActionFailure {
  readonly type = LibraryActionTypes.SetArtistsFilterAlbumsFailure

  constructor(public payload: any) {}

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## SetArtistsFilterAlbumsFailure')
    console.error(this.payload)
    return state
  }
}
