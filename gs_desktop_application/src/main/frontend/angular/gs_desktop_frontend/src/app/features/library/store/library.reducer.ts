import {Action} from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";
import {
  FetchArtistsFailure,
  FetchArtistsSuccess,
  FetchTracksFailure,
  FetchTracksSuccess,
  GsLibraryActionFailure,
  GsLibraryActionSuccess,
  SetArtistsFilterAlbumsFailure,
  SetArtistsFilterAlbumsSuccess,
  SetArtistsFilterTracksFailure,
  SetArtistsFilterTracksSuccess
} from "./library.actions";
import {initialLibraryState, LibraryState} from "./library.state";
import {FetchAlbumsFailure, FetchAlbumsSuccess} from "./actions/albums.actions";

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

    case LibraryActionTypes.SetArtistsFilterAlbumsSuccess:
      success = action as SetArtistsFilterAlbumsSuccess
      return success.handle(state)

    case LibraryActionTypes.SetArtistsFilterAlbumsFailure:
      failure = action as SetArtistsFilterAlbumsFailure
      return failure.handle(state)

    case LibraryActionTypes.SetArtistsFilterTracksSuccess:
      success = action as SetArtistsFilterTracksSuccess
      return success.handle(state)

    case LibraryActionTypes.SetArtistsFilterTracksFailure:
      failure = action as SetArtistsFilterTracksFailure
      return failure.handle(state)

    default:
      return state
  }
}
