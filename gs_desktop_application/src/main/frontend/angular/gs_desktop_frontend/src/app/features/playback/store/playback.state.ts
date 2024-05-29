import {Playlist} from "../../../models/playlist/playlist.model";

export const initialPlaybackState: PlaybackState = {
  currPlaylistTrackIdx: 0,
  playlist: {
    name: "Default Playlist",
    tracks: []
  }
}

export interface PlaybackState {
  currPlaylistTrackIdx: number;
  playlist: Playlist;
}
