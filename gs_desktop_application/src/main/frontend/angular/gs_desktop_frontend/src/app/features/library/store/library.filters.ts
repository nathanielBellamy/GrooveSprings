import {Artist} from "../../../models/artists/artist.model";
import {Album} from "../../../models/albums/album.model";
import {Track} from "../../../models/tracks/track.model";
import {PlaylistRepr} from "../../../models/playlist/playlist_repr.model";

export const emptyLibraryFilters: LibraryFilters = {
  albums: [],
  artists: [],
  playlists: [],
  tracks: [],
}

export interface LibraryFilters {
  albums: Album[];
  artists: Artist[];
  playlists: PlaylistRepr[];
  tracks: Track[];
}
