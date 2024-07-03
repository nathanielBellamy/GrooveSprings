import {Track} from "../tracks/track.model";

export interface TrackSrvr {
  id: number;
  path: string;
  json: string
}

export function trackSrvrFromTrack(track: Track): TrackSrvr {
  return {
    id: track.id,                   // track on server
    path: track.path,               // load audio data on server w/o looking through json
    json: JSON.stringify(track)     // data store
  }
}
