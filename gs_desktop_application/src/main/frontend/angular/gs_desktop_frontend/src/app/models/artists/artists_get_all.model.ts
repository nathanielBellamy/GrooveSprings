import {RestBase} from "../rest_base.model";
import {ArtistsAll} from "./artists_all.model";

export interface ArtistsGetAll extends Omit<RestBase, 'data'> {
  data: ArtistsAll;
}
