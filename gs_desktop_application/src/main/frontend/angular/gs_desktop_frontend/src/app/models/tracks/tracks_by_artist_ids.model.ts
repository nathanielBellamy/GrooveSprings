import {Track} from "./track.model";

export interface TracksByArtistIds {
  artistIds: number[];
  count: number;
  tracks: Track[];
}
