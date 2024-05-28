import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {ArtistsData} from "../../../models/artists/artists_data.model";
import {LibraryState} from "../library.state";
import {GsLibraryActionFailure, GsLibraryActionSuccess} from "../library.actions";

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
