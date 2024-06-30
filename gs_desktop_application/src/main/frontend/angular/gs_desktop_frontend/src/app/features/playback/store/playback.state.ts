import {defaultPlaylist, Playlist} from "../../../models/playlist/playlist.model";
import {defaultTrack, Track} from "../../../models/tracks/track.model";

export const initialPlaybackState: PlaybackState = {
  currPlaylistTrackIdx: 0,
  currTrack: defaultTrack,
  playlist: defaultPlaylist
}

export interface PlaybackState {
  currPlaylistTrackIdx: number;
  currTrack: Track;
  playlist: Playlist;
}
