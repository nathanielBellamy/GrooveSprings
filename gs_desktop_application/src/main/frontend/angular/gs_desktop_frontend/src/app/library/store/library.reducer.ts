import {Action} from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";
import {
  FetchAlbumsFailure,
  FetchAlbumsSuccess,
  FetchArtistsFailure,
  FetchArtistsSuccess, FetchTracksFailure, FetchTracksSuccess,
  GsLibraryActionFailure,
  GsLibraryActionSuccess
} from "./library.actions";
import {initialLibraryState, LibraryState} from "./library.state";

export function libraryReducer(state = initialLibraryState, action: Action): LibraryState {
  var success: GsLibraryActionSuccess | null = null
  var failure: GsLibraryActionFailure | null = null

  switch (action.type) {
    case LibraryActionTypes.FetchArtistsSuccess:
      success = action as FetchArtistsSuccess
      return success.handle(state)

    case LibraryActionTypes.FetchArtistsFailure:
      failure = action as FetchArtistsFailure
      return failure.handle(state)

    case LibraryActionTypes.FetchAlbumsSuccess:
      success = action as FetchAlbumsSuccess
      return success.handle(state)

    case LibraryActionTypes.FetchAlbumsFailure:
      failure = action as FetchAlbumsFailure
      return failure.handle(state)

    case LibraryActionTypes.FetchTracksSuccess:
      success = action as FetchTracksSuccess
      return success.handle(state)

    case LibraryActionTypes.FetchTracksFailure:
      failure = action as FetchTracksFailure
      return failure.handle(state)

    default:
      return state
  }
}
