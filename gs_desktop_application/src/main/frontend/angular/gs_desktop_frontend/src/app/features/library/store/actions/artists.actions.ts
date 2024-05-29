import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionFailure, GsLibraryActionSuccess} from "../library.actions";
import {ArtistsData} from "../../../../models/artists/artists_data.model";

export class FetchArtists implements Action {
  readonly type = LibraryActionTypes.FetchArtists
}

export class FetchArtistsSuccess implements Action, GsLibraryActionSuccess {
  readonly type = LibraryActionTypes.FetchArtistsSuccess

  constructor(public payload: ArtistsData) { }

  handle(state: LibraryState) {
    return {
      ...state,
      artistCount: this.payload.count,
      artists: this.payload.artists
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
