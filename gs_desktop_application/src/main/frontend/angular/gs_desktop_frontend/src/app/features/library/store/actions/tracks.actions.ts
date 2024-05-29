import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionFailure, GsLibraryActionSuccess} from "../library.actions";
import {TracksData} from "../../../../models/tracks/tracks_data.model";

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
    return state
  }
}
