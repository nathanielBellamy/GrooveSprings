import {Artist} from "../../../models/artists/artist.model";
import {Album} from "../../../models/albums/album.model";
import {Track} from "../../../models/tracks/track.model";
import {Playlist} from "../../../models/playlist/playlist.model";

export const emptyLibraryFilters: LibraryFilters = {
  albums: [],
  artists: [],
  playlists: [],
  tracks: [],
}

export interface LibraryFilters {
  albums: Album[];
  artists: Artist[];
  playlists: Playlist[];
  tracks: Track[];
}
