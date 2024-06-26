import {RestBase} from "../rest_base.model";
import {Playlist} from "./playlist.model";

export interface PlaylistUpdateRes extends Omit<RestBase, 'data'> {
  data: {
    playlist: Playlist
  };
}
