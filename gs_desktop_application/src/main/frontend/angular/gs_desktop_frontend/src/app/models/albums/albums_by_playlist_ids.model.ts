import {Album} from "./album.model";

export interface AlbumsByPlaylistIds {
  playlistIds: number[];
  count: number;
  albums: Album[];
}
