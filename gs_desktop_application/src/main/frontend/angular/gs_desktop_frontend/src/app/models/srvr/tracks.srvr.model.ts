import {defaultTrack, Track} from "../tracks/track.model";

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

export function trackFromTrackSrvr(ts: TrackSrvr): Track {
  try {
    return JSON.parse(ts.json) as Track
  } catch(e) {
    console.error(e)
    return defaultTrack
  }
}
