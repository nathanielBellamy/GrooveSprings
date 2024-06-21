import {Playlist} from "../../../models/playlist/playlist.model";
import {defaultTrack, Track} from "../../../models/tracks/track.model";

export const initialPlaybackState: PlaybackState = {
  currPlaylistTrackIdx: 0,
  currTrack: defaultTrack,
  playlist: {
    id: 0,
    name: "Default Playlist",
    tracks: []
  }
}

export interface PlaybackState {
  currPlaylistTrackIdx: number;
  currTrack: Track;
  playlist: Playlist;
}
