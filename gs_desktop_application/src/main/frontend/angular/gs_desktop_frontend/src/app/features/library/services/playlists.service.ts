import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {PlaylistsGetAll} from "../../../models/playlist/playlists_get_all.model";
import {PlaylistsData} from "../../../models/playlist/playlists_data.model";

@Injectable()
export class PlaylistsService {
  constructor(private http: HttpClient) {
  }

  fetchAll(): Observable<PlaylistsData> {
    return this.http.get(`api/v1/playlists`)
      .pipe(
        map(res => {
          const playlistsRes: PlaylistsGetAll = res as PlaylistsGetAll
          const { count, playlists }: PlaylistsData = playlistsRes.data
          return { count, playlists }
        })
      )
  }
}
