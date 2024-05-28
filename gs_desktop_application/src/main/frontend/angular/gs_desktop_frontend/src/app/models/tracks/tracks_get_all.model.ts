import {RestBase} from "../rest_base.model";
import {TracksData} from "./tracks_data.model";

export interface TracksGetAll extends Omit<RestBase, 'data'> {
  data: TracksData;
}
