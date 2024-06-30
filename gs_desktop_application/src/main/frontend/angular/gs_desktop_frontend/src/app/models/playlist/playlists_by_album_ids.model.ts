import {Playlist} from "./playlist.model";

export interface PlaylistsByAlbumIds {
  playlists: Playlist[];
  count: number;
  albumIds: number[];
}
