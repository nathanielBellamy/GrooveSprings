import { Action } from '@ngrx/store'
import { Artist } from "../../models/artists/artists_get_all.model";
import { LibraryActiontypes } from "./library.actiontypes";
import {FetchArtistsSuccess} from "./library.actions";

export interface LibraryState {
  artistCount: number;
  artists: Artist[];
}

export const initialLibraryStore: LibraryState = {
  artistCount: 0,
  artists: []
}

export function libraryReducer(state = initialLibraryStore, action: Action): LibraryState {
  switch (action.type) {
    case LibraryActiontypes.FetchArtists:
      return {...state}

    case LibraryActiontypes.FetchArtistsSuccess:
      const fas = action as FetchArtistsSuccess
      const { count, artists }: { count: number, artists: Artist[] } = fas.payload
      return {
        ...state,
        artistCount: count,
        artists
      }

    default:
      return {...state}
  }
}
