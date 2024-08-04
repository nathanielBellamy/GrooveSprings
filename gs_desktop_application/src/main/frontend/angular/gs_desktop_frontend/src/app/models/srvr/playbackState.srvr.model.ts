import {trackFromTrackSrvr, TrackSrvr, trackSrvrFromTrack} from "./tracks.srvr.model";
import {playlistFromPlaylistSrvr, PlaylistSrvr, playlistSrvrFromPlaylist} from "./playlist.srvr.model";
import {PlaybackState} from "../../features/playback/store/playback.state";
import {GsPlayState} from "../../enums/gsPlayState.enum";
import {GsPlaybackSpeed} from "../../enums/gsPlaybackSpeed.enum";

export interface PlaybackStateSrvr {
  playState: GsPlayState;
  playbackSpeed: GsPlaybackSpeed;
  currFrameId: number;
  currTrack: TrackSrvr;
  currPlaylistTrackIdx: number;
  playlist: PlaylistSrvr;
}

export function playbackStateSrvrFromPlaybackState(ps: PlaybackState): PlaybackStateSrvr {
  return {
    playState: ps.playState,
    playbackSpeed: ps.playbackSpeed,
    currFrameId: ps.currFrameId,
    currTrack: trackSrvrFromTrack(ps.currTrack),
    currPlaylistTrackIdx: ps.currPlaylistTrackIdx,
    playlist: playlistSrvrFromPlaylist(ps.playlist)
  }
}

export function playbackStateFromPlaybackStateSrvr(ps: PlaybackStateSrvr): PlaybackState {
  return {
    playState: ps.playState,
    playbackSpeed: ps.playbackSpeed,
    currFrameId: ps.currFrameId,
    currTrack: trackFromTrackSrvr(ps.currTrack),
    currPlaylistTrackIdx: ps.currPlaylistTrackIdx,
    playlist: playlistFromPlaylistSrvr(ps.playlist)
  }
}
