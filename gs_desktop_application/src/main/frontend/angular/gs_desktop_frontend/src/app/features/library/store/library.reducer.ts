import {Action} from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";
import {
  FetchArtistsFailure,
  FetchArtistsSuccess,
  FetchTracksFailure,
  FetchTracksSuccess,
  GsLibraryActionResult,
  SetArtistsFilterAlbumsFailure,
  SetArtistsFilterAlbumsSuccess,
  SetArtistsFilterTracksFailure,
  SetArtistsFilterTracksSuccess
} from "./library.actions";
import {initialLibraryState, LibraryState} from "./library.state";
import {FetchAlbumsFailure, FetchAlbumsSuccess} from "./actions/albums.actions";

export function libraryReducer(state = initialLibraryState, action: Action): LibraryState {
  var res: GsLibraryActionResult | null = null

  switch (action.type) {
    case LibraryActionTypes.FetchArtistsSuccess:
      res = action as FetchArtistsSuccess
      return res.handle(state)

    case LibraryActionTypes.FetchArtistsFailure:
      res = action as FetchArtistsFailure
      return res.handle(state)

    case LibraryActionTypes.FetchAlbumsSuccess:
      res = action as FetchAlbumsSuccess
      return res.handle(state)

    case LibraryActionTypes.FetchAlbumsFailure:
      res = action as FetchAlbumsFailure
      return res.handle(state)

    case LibraryActionTypes.FetchTracksSuccess:
      res = action as FetchTracksSuccess
      return res.handle(state)

    case LibraryActionTypes.FetchTracksFailure:
      res = action as FetchTracksFailure
      return res.handle(state)

    case LibraryActionTypes.SetArtistsFilterAlbumsSuccess:
      res = action as SetArtistsFilterAlbumsSuccess
      return res.handle(state)

    case LibraryActionTypes.SetArtistsFilterAlbumsFailure:
      res = action as SetArtistsFilterAlbumsFailure
      return res.handle(state)

    case LibraryActionTypes.SetArtistsFilterTracksSuccess:
      res = action as SetArtistsFilterTracksSuccess
      return res.handle(state)

    case LibraryActionTypes.SetArtistsFilterTracksFailure:
      res = action as SetArtistsFilterTracksFailure
      return res.handle(state)

    default:
      return state
  }
}
