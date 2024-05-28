import { Album } from "../../models/albums/album.model";
import {Artist} from "../../models/artists/artist.model";
import {Track} from "../../models/tracks/track.model";

export interface LibraryState {
  albumCount: number;
  albums: Album[];
  artistCount: number;
  artists: Artist[];
  trackCount: number;
  tracks: Track[];
}

export const initialLibraryState: LibraryState = {
  albumCount: 0,
  albums: [],
  artistCount: 0,
  artists: [],
  trackCount: 0,
  tracks: [],
}
