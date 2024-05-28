import { Action } from '@ngrx/store'
import {LibraryActiontypes} from "./library.actiontypes";

export class FetchArtists implements Action {
  readonly type = LibraryActiontypes.FetchArtists
}

export class FetchArtistsSuccess implements Action {
  readonly type = LibraryActiontypes.FetchArtistsSuccess

  constructor(public payload: any) { }
}

export class FetchArtistsFailure implements Action {
  readonly type = LibraryActiontypes.FetchArtistsFailure

  constructor(public payload: any) { }
}
