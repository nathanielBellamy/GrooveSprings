import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {AlbumsGetAll} from "../../../models/albums/albums_get_all.model";
import {AlbumsData} from "../../../models/albums/albums_data.model";
import {AlbumsGetByArtistIds} from "../../../models/albums/albums_get_by_artist_ids.model";
import {AlbumsByArtistIds} from "../../../models/albums/albums_by_artist_ids.model";
import {AlbumsByPlaylistIds} from "../../../models/albums/albums_by_playlist_ids.model";
import {AlbumsGetByPlaylistIds} from "../../../models/albums/albums_get_by_playlist_ids.model";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../store/library.actiontypes";
import {SetArtistsFilter, SetPlaylistsFilter} from "../store/library.actions";

@Injectable()
export class AlbumsService {
  constructor(private http: HttpClient) { }

  fetchByAction(action: Action): Observable<AlbumsData | AlbumsByArtistIds | AlbumsByPlaylistIds> {
    switch (action.type) {
      case LibraryActionTypes.SetArtistsFilter:
        const sarf = action as SetArtistsFilter
        return this.fetchByArtistIds(sarf.payload.map(ar => ar.id))
      case LibraryActionTypes.SetPlaylistsFilter:
        const splf = action as SetPlaylistsFilter
        return this.fetchByPlaylistIds(splf.payload.map(pl => pl.id))
      default:
        return this.fetchAll()
    }
  }

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

  fetchByPlaylistIds(playlistIds: number[]): Observable<AlbumsByPlaylistIds> {
    return this.http.post(`api/v1/albums/byPlaylistIds`, { playlistIds })
      .pipe(
        map(res => {
          const albumsRes: AlbumsGetByPlaylistIds = res as AlbumsGetByPlaylistIds
          return albumsRes.data
        })
      )
  }
}
