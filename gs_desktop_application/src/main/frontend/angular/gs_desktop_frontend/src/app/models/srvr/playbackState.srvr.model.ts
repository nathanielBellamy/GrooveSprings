import {TrackSrvr, trackSrvrFromTrack} from "./tracks.srvr.model";
import {PlaylistSrvr, playlistSrvrFromPlaylist} from "./playlist.srvr.model";
import {PlaybackState} from "../../features/playback/store/playback.state";

export interface PlaybackStateSrvr {
  currTrack: TrackSrvr;
  currPlaylistTrackIdx: number;
  playlist: PlaylistSrvr;
}

export function playbackStateSrvrFromPlaybackState(ps: PlaybackState): PlaybackStateSrvr {
  return {
    currTrack: trackSrvrFromTrack(ps.currTrack),
    currPlaylistTrackIdx: ps.currPlaylistTrackIdx,
    playlist: playlistSrvrFromPlaylist(ps.playlist)
  }
}
