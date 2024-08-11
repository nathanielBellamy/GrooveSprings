import {defaultPlaylist, Playlist} from "../../../models/playlist/playlist.model";
import {defaultTrack, Track} from "../../../models/tracks/track.model";
import {GsPlayState} from "../../../enums/gsPlayState.enum";
import {GsPlaybackSpeed} from "../../../enums/gsPlaybackSpeed.enum";
import {GsLoopType} from "../../../enums/gsLoopType.enum";

export const initialPlaybackState: PlaybackState = {
  playState: GsPlayState.STOP,
  playbackSpeed: GsPlaybackSpeed._1,
  loopType: GsLoopType.NONE,
  shuffle: false,
  currFrameId: 0,
  currPlaylistTrackIdx: 0,
  currTrack: defaultTrack,
  playlist: defaultPlaylist
}

export interface PlaybackState {
  playState: GsPlayState;
  playbackSpeed: GsPlaybackSpeed;
  loopType: GsLoopType;
  shuffle: boolean;
  currFrameId: number;
  currPlaylistTrackIdx: number;
  currTrack: Track;
  playlist: Playlist;
}
