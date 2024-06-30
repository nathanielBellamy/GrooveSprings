import {Artist} from "../artists/artist.model";

export const defaultAlbum: Album = {
  id: 0,
  title: "-",
  releaseDate: 0
}

export interface Album {
  id: number;
  title: string;
  releaseDate: number;
}
