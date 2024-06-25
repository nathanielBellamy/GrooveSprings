import {Playlist} from "./playlist.model";

export interface PlaylistsByArtistIds {
  playlists: Playlist[];
  count: number;
  artistIds: number[];
}
