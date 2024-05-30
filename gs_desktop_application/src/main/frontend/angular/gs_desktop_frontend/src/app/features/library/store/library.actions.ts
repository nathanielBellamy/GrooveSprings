import {LibraryState} from "./library.state";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "./library.actiontypes";

export abstract class GsLibraryActionResult {
  public payload: any

  handle(state: LibraryState): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionResult")
  }
}

export class ClearArtistsFilter implements Action {
  readonly type = LibraryActionTypes.ClearArtistsFilter
}

export * from './actions/albums.actions'
export * from './actions/artists.actions'
export * from './actions/tracks.actions'
