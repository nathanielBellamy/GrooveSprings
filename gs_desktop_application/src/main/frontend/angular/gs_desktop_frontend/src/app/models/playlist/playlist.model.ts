import {Track} from "../tracks/track.model";

export const defaultPlaylist = {
  id: 0,
  name: "",
  tracks: []
}

export interface Playlist {
  id: number;
  name: string;
  tracks: Track[];
}
