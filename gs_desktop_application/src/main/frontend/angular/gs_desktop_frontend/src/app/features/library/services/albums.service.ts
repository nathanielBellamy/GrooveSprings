import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {AlbumsGetAll} from "../../../models/albums/albums_get_all.model";
import {AlbumsData} from "../../../models/albums/albums_data.model";
import {AlbumsGetByArtistIds} from "../../../models/albums/albums_get_by_artist_ids.model";
import {AlbumsByArtistIds} from "../../../models/albums/albums_by_artist_ids.model";

@Injectable()
export class AlbumsService {
  constructor(private http: HttpClient) { }

  fetchAll(): Observable<AlbumsData> {
    return this.http.get(`api/v1/albums`)
      .pipe(
        map(res => {
          const albumsRes: AlbumsGetAll = res as AlbumsGetAll
          const { count, albums }: AlbumsData  = albumsRes.data
          return { count, albums }
        })
      )
  }

  fetchByArtistIds(artistIds: number[]): Observable<AlbumsByArtistIds> {
    return this.http.post(`api/v1/albums/byArtistIds`, { artistIds })
      .pipe(
        map(res => {
          const albumsRes: AlbumsGetByArtistIds = res as AlbumsGetByArtistIds
          return albumsRes.data
        })
      )
  }

}
