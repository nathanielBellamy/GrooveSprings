import {RestBase} from "../rest_base.model";
import {AlbumsData} from "./albums_data.model";

export interface AlbumsGetAll extends Omit<RestBase, 'data'> {
  data: AlbumsData;
}
