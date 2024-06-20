import {Track} from "../tracks/track.model";

export const defaultPlaylist = {
  name: "",
  tracks: []
}

export interface Playlist {
  name: string;
  tracks: Track[];
}
