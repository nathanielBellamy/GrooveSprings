import {Artist} from "../../../models/artists/artist.model";
import {Album} from "../../../models/albums/album.model";
import {Track} from "../../../models/tracks/track.model";

export const emptyLibraryFilters: LibraryFilters = {
  albums: [],
  artists: [],
  tracks: [],
}

export interface LibraryFilters {
  albums: Album[];
  artists: Artist[];
  tracks: Track[];
}
