import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionResult} from "../library.actions";
import {TracksData} from "../../../../models/tracks/tracks_data.model";
import {TracksByArtistIds} from "../../../../models/tracks/tracks_by_artist_ids.model";
import {TracksByAlbumIds} from "../../../../models/tracks/tracks_by_album_ids.model";

export class FetchTracks implements Action {
  readonly type = LibraryActionTypes.FetchTracks
}

export class FetchTracksSuccess implements Action, GsLibraryActionResult {
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

export class FetchTracksFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.FetchTracksFailure

  constructor(public payload: any) {}

  handle(state: LibraryState) {
    console.error('GsLibraryActionFailure ## FetchTrackFailure')
    console.error(this.payload)
    return state
  }
}

export class SetArtistsFilterTracksSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetArtistsFilterTracksSuccess

  constructor(public payload: TracksByArtistIds) {}

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      trackCount: this.payload.count,
      tracks: this.payload.tracks,
      filters: {
        ...state.filters,
        artists: state.artists.filter(artist => this.payload.artistIds.includes(artist.id)),
      }
    }
  }
}

export class SetArtistsFilterTracksFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetArtistsFilterTracksFailure

  constructor(public payload: any) {}

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## FetchTrackFailure')
    console.error(this.payload)
    return state
  }
}

export class SetAlbumsFilterTracksSuccess implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetAlbumsFilterTracksSuccess

  constructor(public payload: TracksByAlbumIds) {}

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      tracks: this.payload.tracks,
      trackCount: this.payload.count,
      filters: {
        ...state.filters,
        albums: state.albums.filter(a => this.payload.albumIds.includes(a.id))
      }
    }
  }
}

export class SetAlbumsFilterTracksFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.SetAlbumsFilterTracksFailure

  constructor(public payload: any) {}

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## SetAlbumsFilterTracksFailure')
    console.error(this.payload)
    return state
  }
}
