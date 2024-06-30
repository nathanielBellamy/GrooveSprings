import {RestBase} from "../rest_base.model";
import {ArtistsData} from "./artists_data.model";

export interface ArtistsGetAll extends Omit<RestBase, 'data'> {
  data: ArtistsData;
}
