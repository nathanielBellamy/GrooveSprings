import {trackFromTrackSrvr, TrackSrvr, trackSrvrFromTrack} from "./tracks.srvr.model";
import {Playlist} from "../playlist/playlist.model";

export interface PlaylistSrvr {
  id: number;
  name: string;
  tracks: TrackSrvr[];
}

export function playlistSrvrFromPlaylist(pl: Playlist): PlaylistSrvr {
  return {
    id: pl.id,
    name: pl.name,
    tracks: pl.tracks.map(trackSrvrFromTrack)
  }
}

export function playlistFromPlaylistSrvr(pls: PlaylistSrvr): Playlist {
  return {
    id: pls.id,
    name: pls.name,
    tracks: pls.tracks.map(trackFromTrackSrvr)
  }
}
