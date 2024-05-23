import {RestBase} from "../rest_base.model";

export interface Artist {
  id: number;
  name: string;
}

export interface ArtistData {
  count: number;
  artists: Artist[];
}

export interface ArtistsGetAll extends Omit<RestBase, 'data'> {
  data: ArtistData;
}
