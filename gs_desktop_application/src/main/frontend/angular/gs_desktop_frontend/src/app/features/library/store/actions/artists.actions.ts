import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionResult} from "../library.actions";
import {ArtistsAll} from "../../../../models/artists/artists_all.model";
import {ArtistsByAlbumIds} from "../../../../models/artists/artists_by_album_ids.model";
import {ArtistsByPlaylistIds} from "../../../../models/artists/artists_by_playlist_ids.model";

export class FetchArtists implements Action {
  readonly type = LibraryActionTypes.FetchArtists
}

export class FetchArtistsSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.FetchArtistsSuccess

  constructor(public payload: ArtistsAll) { }

  handle(state: LibraryState) {
    return {
      ...state,
      artistCount: this.payload.count,
      artists: this.payload.artists
    }
  }
}

export class FetchArtistsFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.FetchArtistsFailure

  constructor(public payload: any) { }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## FetchArtistsFailure')
    console.error(this.payload)
    return state
  }
}

export class SetAlbumsFilterArtistsSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetAlbumsFilterArtistsSuccess

  constructor(public payload: ArtistsByAlbumIds) {}

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      artists: this.payload.artists,
      artistCount: this.payload.count,
      filters: {
        ...state.filters,
        albums: state.albums.filter(a => this.payload.albumIds.includes(a.id))
      }
    }
  }
}

export class SetAlbumsFilterArtistsFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetAlbumsFilterArtistsFailure

  constructor(public payload: any) { }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## SetAlbumsFilterArtistsFailure')
    console.error(this.payload)
    return state
  }
}

export class SetPlaylistsFilterArtistsSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetPlaylistsFilterArtistsSuccess

  constructor(public payload: ArtistsByPlaylistIds) { }

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      artists: this.payload.artists,
      artistCount: this.payload.count,
      filters: {
        ...state.filters,
        albums: state.albums.filter(a => this.payload.playlistIds.includes(a.id))
      }
    }
  }
}

export class SetPlaylistsFilterArtistsFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetPlaylistsFilterArtistsFailure

  constructor(public payload: any) { }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## SetPlaylistsFilterArtistsFailure')
    console.error(this.payload)
    return state
  }
}
