import {Album} from "../albums/album.model";
import {Artist} from "../artists/artist.model";

export interface Track {
  id: number;
  title: string;
  duration: number;
  trackNumber: number;
  genres: string[];
  audioCodec: string;
  releaseDate: string;
  path: string;
  album: Album;
  artists: Artist[];
}
