import {Album} from "./album.model";

export interface AlbumsByArtistIds {
  artistIds: number[];
  count: number;
  albums: Album[];
}
