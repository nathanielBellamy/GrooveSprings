import {LibraryState} from "./library.state";

export abstract class GsLibraryActionSuccess {
  public payload: any

  handle(state: LibraryState): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionSuccess")
  }
}

export abstract class GsLibraryActionFailure {
  public payload: any

  handle(error: any): LibraryState {
    throw new Error("Method 'handle()' must be implemented by GsLibraryActionFailure")
  }
}

export * from './actions/albums.actions'
export * from './actions/artists.actions'
export * from './actions/tracks.actions'
