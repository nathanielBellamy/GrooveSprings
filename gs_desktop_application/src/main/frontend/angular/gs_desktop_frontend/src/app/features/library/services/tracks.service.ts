import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {TracksGetAll} from "../../../models/tracks/tracks_get_all.model";
import {TracksData} from "../../../models/tracks/tracks_data.model";
import {TracksGetByArtistIds} from "../../../models/tracks/tracks_get_by_artist_ids.model";

@Injectable()
export class TracksService {
  constructor(private http: HttpClient) { }

  fetchAll(): Observable<TracksData> {
    return this.http.get(`api/v1/tracks`)
      .pipe(
        map(res => {
          const tracksRes: TracksGetAll = res as TracksGetAll
          const { count, tracks }: TracksData  = tracksRes.data
          return { count, tracks }
        })
      )
  }

  fetchByArtistIds(artistIds: number[]): Observable<TracksData> {
    return this.http.post(`api/v1/tracks/byArtistIds`, { artistIds })
      .pipe(
        map(res => {
          const tracksRes: TracksGetByArtistIds = res as TracksGetByArtistIds
          const { count, tracks }: TracksData = tracksRes.data
          return { count, tracks }
        })
      )
  }

}
