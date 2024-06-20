import {RestBase} from "../rest_base.model";
import {Playlist} from "./playlist.model";

export interface PlaylistCreateRes extends Omit<RestBase, 'data'> {
  data: {
    playlist: Playlist
  };
}
