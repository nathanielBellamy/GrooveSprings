import { Track } from './track.model'

export interface TracksByPlaylistIds {
  playlistIds: number[];
  count: number;
  tracks: Track[];
}
