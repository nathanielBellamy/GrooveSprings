import { createReducer, on } from '@ngrx/store'
import {fetchArtistsSuccess} from './library.actions'
import {Artist} from "../../models/artists/artists_get_all.model";

export interface LibraryStore {
  artistCount: number;
  artists: Artist[];
}

export const initialLibraryStore: LibraryStore = {
  artistCount: 0,
  artists: []
}

export const libraryReducer = createReducer(
  initialLibraryStore,
  on(fetchArtistsSuccess, (store: LibraryStore, action: any) => {
    const { artistCount, artists }: { artistCount: number, artists: Artist[] } = action.payload

    console.dir({artistCount, artists})
    return {
      ...store,
      artistCount,
      artists
    }
  })
)
