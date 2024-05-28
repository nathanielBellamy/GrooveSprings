import { Action } from '@ngrx/store'
import { Artist } from "../../models/artists/artists_get_all.model";
import { LibraryActiontypes } from "./library.actiontypes";
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
  switch (action.type) {
    case LibraryActiontypes.FetchArtists:
      return {...state}

    case LibraryActiontypes.FetchArtistsSuccess:
      const fas = action as FetchArtistsSuccess
      const { count, artists }: { count: number, artists: Artist[] } = fas.payload
      const res = {
        ...state,
        artistsCount: count,
        artists
      }

      console.dir({res})
      return res

    default:
      return {...state}
  }
}
