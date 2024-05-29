import {Track} from "./track.model";

export interface TracksGetByArtistIds {
  data: {
    artistIds: number[];
    count: number;
    tracks: Track[];
  }
}
