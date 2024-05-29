import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
import {TracksGetAll} from "../../../models/tracks/tracks_get_all.model";
import {TracksData} from "../../../models/tracks/tracks_data.model";

@Injectable()
export class TracksService {
  constructor(private http: HttpClient) { }

  fetchAll() {
    return this.http.get(`api/v1/tracks`)
      .pipe(
        map(res => {
          const tracksRes: TracksGetAll = res as TracksGetAll
          const { count, tracks }: TracksData  = tracksRes.data
          return { count, tracks }
        })
      )
  }

}
