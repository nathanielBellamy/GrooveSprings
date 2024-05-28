import { Album } from "../../models/albums/album.model";
import {Artist} from "../../models/artists/artist.model";

export interface LibraryState {
  albumCount: number;
  albums: Album[];
  artistCount: number;
  artists: Artist[];
}

export const initialLibraryState: LibraryState = {
  albumCount: 0,
  albums: [],
  artistCount: 0,
  artists: []
}
