import {Album, defaultAlbum} from "../albums/album.model";
import {Artist} from "../artists/artist.model";

export const defaultTrack: Track = {
  id: 0,
  title: "-",
  duration: 0,
  trackNumber: 0,
  genres: [],
  audioCodec: "-",
  releaseDate: "-",
  path: "-",
  album: defaultAlbum,
  artists: [],
  sf_frames: 0,
  sf_channels: 0,
  sf_samplerate: 0,
  bitRate: 0
}

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
  sf_frames: number;
  sf_channels: number;
  sf_samplerate: number;
  bitRate: number;
}
