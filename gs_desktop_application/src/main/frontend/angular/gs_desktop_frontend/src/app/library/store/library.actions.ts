import { Action } from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";

export class FetchArtists implements Action {
  readonly type = LibraryActionTypes.FetchArtists
}

export class FetchArtistsSuccess implements Action {
  readonly type = LibraryActionTypes.FetchArtistsSuccess

  constructor(public payload: any) { }
}

export class FetchArtistsFailure implements Action {
  readonly type = LibraryActionTypes.FetchArtistsFailure

  constructor(public payload: any) { }
}
