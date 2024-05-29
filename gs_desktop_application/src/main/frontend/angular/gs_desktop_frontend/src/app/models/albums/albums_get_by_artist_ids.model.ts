import {Album} from "./album.model";

export interface AlbumsGetByArtistIds {
  artistIds: number[];
  count: number;
  albums: Album[];
}
