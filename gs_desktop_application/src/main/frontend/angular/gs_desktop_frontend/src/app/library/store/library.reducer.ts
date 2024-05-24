import { Action } from '@ngrx/store'
import { Artist } from "../../models/artists/artists_get_all.model";
import { LibraryActionTypes } from "./library.actiontypes";
import {FetchArtistsSuccess} from "./library.actions";

export interface LibraryStore {
  artistCount: number;
  artists: Artist[];
}

export const initialLibraryStore: LibraryStore = {
  artistCount: 0,
  artists: []
}

export function libraryReducer(state = initialLibraryStore, action: Action) {
  alert('reducer');
  switch (action.type) {
    case LibraryActionTypes.FetchArtists:
      alert("library.reducer FetchArtists")
      return {...state}

    case LibraryActionTypes.FetchArtistsSuccess:
      const fas = action as FetchArtistsSuccess
      const { count, artists }: { count: number, artists: Artist[] } = fas.payload
      return {
        ...state,
        artistsCount: count,
        artists
      }

    default:
      return {...state}
  }
}
