import {Album} from "../../../models/albums/album.model";
import {Artist} from "../../../models/artists/artist.model";
import {Track} from "../../../models/tracks/track.model";
import {emptyLibraryFilters, LibraryFilters} from "./library.filters";

export interface LibraryState {
  albumCount: number;
  albums: Album[];
  artistCount: number;
  artists: Artist[];
  filters: LibraryFilters;
  trackCount: number;
  tracks: Track[];
}

export const initialLibraryState: LibraryState = {
  albumCount: 0,
  albums: [],
  artistCount: 0,
  artists: [],
  filters: emptyLibraryFilters,
  trackCount: 0,
  tracks: [],
}
