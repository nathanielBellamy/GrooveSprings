import {LibraryState} from "./library.state";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "./library.actiontypes";

export abstract class GsLibraryActionResult {
  public payload: any

  handle(state: LibraryState): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionResult")
  }
}

export class FetchAll implements Action {
  readonly type = LibraryActionTypes.FetchAll
}

export class ClearArtistsFilter implements Action, GsLibraryActionResult {
  readonly type = LibraryActionTypes.ClearArtistsFilter

  public payload: null = null

  handle(state: LibraryState): LibraryState {
    return {
      ...state,
      filters: {
        ...state.filters,
        artists: []
      }
    }
  }
}

export * from './actions/albums.actions'
export * from './actions/artists.actions'
export * from './actions/tracks.actions'
