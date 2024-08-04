import {defaultPlaylist, Playlist} from "../../../models/playlist/playlist.model";
import {defaultTrack, Track} from "../../../models/tracks/track.model";
import {GsPlayState} from "../../../enums/gsPlayState.enum";
import {GsPlaybackSpeed} from "../../../enums/gsPlaybackSpeed.enum";

export const initialPlaybackState: PlaybackState = {
  playState: GsPlayState.STOP,
  playbackSpeed: GsPlaybackSpeed._1,
  currFrameId: 0,
  currPlaylistTrackIdx: 0,
  currTrack: defaultTrack,
  playlist: defaultPlaylist
}

export interface PlaybackState {
  playState: GsPlayState;
  playbackSpeed: GsPlaybackSpeed;
  currFrameId: number;
  currPlaylistTrackIdx: number;
  currTrack: Track;
  playlist: Playlist;
}
