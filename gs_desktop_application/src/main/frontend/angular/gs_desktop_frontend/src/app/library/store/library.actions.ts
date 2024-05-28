import { Action } from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";
import {ArtistsData} from "../../models/artists/artists_data.model";
import {AlbumsData} from "../../models/albums/albums_data.model";
import {LibraryState} from "./library.state";
import {TracksData} from "../../models/tracks/tracks_data.model";

export abstract class GsLibraryActionSuccess {
  public payload: any

  handle(state: LibraryState): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionSuccess")
  }
}

export abstract class GsLibraryActionFailure {
  public payload: any

  handle(error: any): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionFailure")
  }
}

export class FetchArtists implements Action {
  readonly type = LibraryActionTypes.FetchArtists
}

export class FetchArtistsSuccess implements Action, GsLibraryActionSuccess {
  readonly type = LibraryActionTypes.FetchArtistsSuccess

  constructor(public payload: ArtistsData) { }

  handle(state: LibraryState) {
    const { count, artists }: ArtistsData = this.payload
    return {
      ...state,
      artistCount: count,
      artists
    }
  }
}

export class FetchArtistsFailure implements Action, GsLibraryActionFailure {
  readonly type = LibraryActionTypes.FetchArtistsFailure

  constructor(public payload: any) { }

  handle(state: LibraryState) {
    console.error('GsLibraryActionFailure ## FetchArtistsFailure')
    console.error(this.payload)
    return state
  }
}

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

  constructor(public payload: any) { }

  handle(state: LibraryState) {
    console.error('GsLibraryActionFailure ## FetchAlbumsFailure')
    console.error(this.payload)
    return state
  }
}

export class FetchTracks implements Action {
  readonly type = LibraryActionTypes.FetchTracks
}

export class FetchTracksSuccess implements Action, GsLibraryActionSuccess {
  readonly type = LibraryActionTypes.FetchTracksSuccess

  constructor(public payload: TracksData) { }

  handle(state: LibraryState) {
    return {
      ...state,
      trackCount: this.payload.count,
      tracks: this.payload.tracks
    }
  }
}

export class FetchTracksFailure implements Action, GsLibraryActionFailure {
  readonly type = LibraryActionTypes.FetchTracksFailure

  constructor(public payload: any) {}

  handle(state: LibraryState) {
    console.error('GsLibraryActionFailure ## FetchTrackFailure')
    console.error(this.payload)
    return state
  }
}
