import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionFailure, GsLibraryActionSuccess} from "../library.actions";
import {TracksData} from "../../../../models/tracks/tracks_data.model";
import {TracksGetByArtistIds} from "../../../../models/tracks/tracks_get_by_artist_ids.model";
import {TracksByArtistIds} from "../../../../models/tracks/tracks_by_artist_ids.model";

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

export class SetArtistsFilterTracksSuccess implements Action, GsLibraryActionSuccess {
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

export class SetArtistsFilterTracksFailure implements Action, GsLibraryActionFailure {
  readonly type = LibraryActionTypes.SetArtistsFilterTracksFailure

  constructor(public payload: any) {}

  handle(state: LibraryState) {
    console.error('GsLibraryActionFailure ## FetchTrackFailure')
    console.error(this.payload)
    return state
  }
}
