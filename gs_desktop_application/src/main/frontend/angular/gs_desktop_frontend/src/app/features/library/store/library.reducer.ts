import {Action} from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";
import {
  ClearAlbumsFilter,
  ClearArtistsFilter, ClearLibrary,
  FetchArtistsFailure,
  FetchArtistsSuccess,
  FetchTracksFailure,
  FetchTracksSuccess,
  GsLibraryActionResult,
  Identity,
  SetAlbumsFilterArtistsSuccess,
  SetAlbumsFilterTracksFailure,
  SetAlbumsFilterTracksSuccess,
  SetArtistsFilterAlbumsFailure,
  SetArtistsFilterAlbumsSuccess,
  SetArtistsFilterTracksFailure,
  SetArtistsFilterTracksSuccess
} from "./library.actions";
import {initialLibraryState, LibraryState} from "./library.state";
import {FetchAlbumsFailure, FetchAlbumsSuccess} from "./actions/albums.actions";

export function libraryReducer(state = initialLibraryState, action: Action): LibraryState {
  let res: GsLibraryActionResult

  switch (action.type) {
    // fetch actions
    case LibraryActionTypes.FetchArtistsSuccess:
      res = action as FetchArtistsSuccess
      break

    case LibraryActionTypes.FetchArtistsFailure:
      res = action as FetchArtistsFailure
      break

    case LibraryActionTypes.FetchAlbumsSuccess:
      res = action as FetchAlbumsSuccess
      break

    case LibraryActionTypes.FetchAlbumsFailure:
      res = action as FetchAlbumsFailure
      break

    case LibraryActionTypes.FetchTracksSuccess:
      res = action as FetchTracksSuccess
      break

    case LibraryActionTypes.FetchTracksFailure:
      res = action as FetchTracksFailure
      break

    // filter actions
    case LibraryActionTypes.SetArtistsFilterAlbumsSuccess:
      res = action as SetArtistsFilterAlbumsSuccess
      break

    case LibraryActionTypes.SetArtistsFilterAlbumsFailure:
      res = action as SetArtistsFilterAlbumsFailure
      break

    case LibraryActionTypes.SetArtistsFilterTracksSuccess:
      res = action as SetArtistsFilterTracksSuccess
      break

    case LibraryActionTypes.SetArtistsFilterTracksFailure:
      res = action as SetArtistsFilterTracksFailure
      break

    case LibraryActionTypes.ClearArtistsFilter:
      res = action as ClearArtistsFilter
      break

    case LibraryActionTypes.SetAlbumsFilterArtistsSuccess:
      res = action as SetAlbumsFilterArtistsSuccess
      break

    case LibraryActionTypes.SetAlbumsFilterTracksSuccess:
      res = action as SetAlbumsFilterTracksSuccess
      break

    case LibraryActionTypes.SetAlbumsFilterTracksFailure:
      res = action as SetAlbumsFilterTracksFailure
      break

    case LibraryActionTypes.ClearAlbumsFilter:
      res = action as ClearAlbumsFilter
      break

    case LibraryActionTypes.ClearLibrary:
      res = action as ClearLibrary
      break

    default:
      res = new Identity()
  }

  return res.handle(state)
}
