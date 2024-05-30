import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../library.actiontypes";
import {LibraryState} from "../library.state";
import {GsLibraryActionResult} from "../library.actions";
import {ArtistsData} from "../../../../models/artists/artists_data.model";
import {Artist} from "../../../../models/artists/artist.model";

export class FetchArtists implements Action {
  readonly type = LibraryActionTypes.FetchArtists
}

export class FetchArtistsSuccess implements Action, GsLibraryActionResult {
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

export class FetchArtistsFailure implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.FetchArtistsFailure

  constructor(public payload: any) { }

  handle(state: LibraryState): LibraryState {
    console.error('GsLibraryActionFailure ## FetchArtistsFailure')
    console.error(this.payload)
    return state
  }
}

export class SetArtistsFilter implements Action {
  readonly type = LibraryActionTypes.SetArtistsFilter

  constructor(public payload: Artist[]) { }
}
