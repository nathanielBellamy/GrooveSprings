import {trackFromTrackSrvr, TrackSrvr, trackSrvrFromTrack} from "./tracks.srvr.model";
import {playlistFromPlaylistSrvr, PlaylistSrvr, playlistSrvrFromPlaylist} from "./playlist.srvr.model";
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

export function playbackStateFromPlaybackStateSrvr(ps: PlaybackStateSrvr): PlaybackState {
  return {
    currTrack: trackFromTrackSrvr(ps.currTrack),
    currPlaylistTrackIdx: ps.currPlaylistTrackIdx,
    playlist: playlistFromPlaylistSrvr(ps.playlist)
  }
}
