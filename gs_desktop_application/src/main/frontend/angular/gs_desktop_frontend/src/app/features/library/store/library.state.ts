import {Album} from "../../../models/albums/album.model";
import {Artist} from "../../../models/artists/artist.model";
import {Track} from "../../../models/tracks/track.model";
import {emptyLibraryFilters, LibraryFilters} from "./library.filters";
import {Playlist} from "../../../models/playlist/playlist.model";

export interface LibraryState {
  albumCount: number;
  albums: Album[];
  artistCount: number;
  artists: Artist[];
  filters: LibraryFilters;
  playlistCount: number;
  playlists: Playlist[];
  trackCount: number;
  tracks: Track[];
}

export const initialLibraryState: LibraryState = {
  albumCount: 0,
  albums: [],
  artistCount: 0,
  artists: [],
  filters: emptyLibraryFilters,
  playlistCount: 0,
  playlists: [],
  trackCount: 0,
  tracks: [],
}
