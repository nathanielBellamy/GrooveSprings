import {RestBase} from "../rest_base.model";
import {PlaylistsData} from "./playlists_data.model";

export interface PlaylistsGetAll extends Omit<RestBase, 'data'> {
  data: PlaylistsData;
}
