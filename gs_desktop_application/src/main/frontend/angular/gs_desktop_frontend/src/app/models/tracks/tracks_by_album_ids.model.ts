import {Track} from "./track.model";

export interface TracksByAlbumIds {
  albumIds: number[];
  count: number;
  tracks: Track[];
}
