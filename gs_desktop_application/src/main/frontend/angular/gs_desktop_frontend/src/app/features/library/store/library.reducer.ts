import {Action} from '@ngrx/store'
import {LibraryActionTypes} from "./library.actiontypes";
import {
  ClearFilters,
  ClearLibrary,
  FetchArtistsFailure,
  FetchArtistsSuccess,
  FetchTracksFailure,
  FetchTracksSuccess,
  GsLibraryActionResult,
  Identity,
  SetAlbumsFilter,
  SetAlbumsFilterArtistsSuccess,
  SetAlbumsFilterTracksFailure,
  SetAlbumsFilterTracksSuccess,
  SetArtistsFilter,
  SetArtistsFilterAlbumsFailure,
  SetArtistsFilterAlbumsSuccess,
  SetArtistsFilterTracksFailure,
  SetArtistsFilterTracksSuccess,
  SetPlaylistsFilter, SetPlaylistsFilterAlbumsFailure,
  SetPlaylistsFilterAlbumsSuccess, SetPlaylistsFilterArtistsFailure, SetPlaylistsFilterArtistsSuccess,
  SetPlaylistsFilterTracksFailure,
  SetPlaylistsFilterTracksSuccess
} from "./library.actions";
import {initialLibraryState, LibraryState} from "./library.state";
import {FetchAlbumsFailure, FetchAlbumsSuccess} from "./actions/albums.actions";
import {FetchPlaylistsFailure, FetchPlaylistsSuccess} from "./actions/playlists.actions";

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

    case LibraryActionTypes.ClearFilters:
      res = action as ClearFilters
      break

    case LibraryActionTypes.SetPlaylistsFilter:
      res = action as SetPlaylistsFilter
      break


    case LibraryActionTypes.SetArtistsFilter:
      res = action as SetArtistsFilter
      break

    case LibraryActionTypes.SetAlbumsFilter:
      res = action as SetAlbumsFilter
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

    case LibraryActionTypes.SetPlaylistsFilterTracksSuccess:
      res = action as SetPlaylistsFilterTracksSuccess
      break

    case LibraryActionTypes.SetPlaylistsFilterTracksFailure:
      res = action as SetPlaylistsFilterTracksFailure
      break

    case LibraryActionTypes.SetPlaylistsFilterAlbumsSuccess:
      res = action as SetPlaylistsFilterAlbumsSuccess
      break

    case LibraryActionTypes.SetPlaylistsFilterAlbumsFailure:
      res = action as SetPlaylistsFilterAlbumsFailure
      break

    case LibraryActionTypes.SetPlaylistsFilterArtistsSuccess:
      res = action as SetPlaylistsFilterArtistsSuccess
      break

    case LibraryActionTypes.SetPlaylistsFilterArtistsFailure:
      res = action as SetPlaylistsFilterArtistsFailure
      break

    case LibraryActionTypes.FetchPlaylistsSuccess:
      res = action as FetchPlaylistsSuccess
      break

    case LibraryActionTypes.FetchPlaylistsFailure:
      res = action as FetchPlaylistsFailure
      break

    case LibraryActionTypes.ClearLibrary:
      res = action as ClearLibrary
      break

    default:
      res = new Identity()
  }

  return res.handle(state)
}
