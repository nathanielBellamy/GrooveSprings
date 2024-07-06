import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {PlaylistsGetAll} from "../../../models/playlist/playlists_get_all.model";
import {PlaylistsData} from "../../../models/playlist/playlists_data.model";
import {Playlist} from "../../../models/playlist/playlist.model";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../store/library.actiontypes";
import {SetAlbumsFilter, SetArtistsFilter} from "../store/library.actions";
import {PlaylistsByArtistIds} from "../../../models/playlist/playlists_by_artist_ids.model";
import {PlaylistsGetByArtistIds} from "../../../models/playlist/playlists_get_by_artist_ids.model";
import {PlaylistsByAlbumIds} from "../../../models/playlist/playlists_by_album_ids.model";
import {PlaylistsGetByAlbumIds} from "../../../models/playlist/playlists_get_by_album_ids.model";
import {PlaylistUpdateDto} from "../../../models/playlist/playlist_update_dto.model";

@Injectable()
export class PlaylistsService {
  private fetchUrl: string = "api/v1/playlists"
  private crudUrl: string = "api/v1/playlistCrud"

  constructor(private http: HttpClient) {
  }

  fetchByAction(action: Action): Observable<PlaylistsData | PlaylistsByAlbumIds | PlaylistsByArtistIds> {
    switch (action.type) {
      case LibraryActionTypes.SetArtistsFilter:
        const sarf = action as SetArtistsFilter
        return this.fetchByArtistIds(sarf.payload.map(ar => ar.id))

      case LibraryActionTypes.SetAlbumsFilter:
        const salf = action as SetAlbumsFilter
        return this.fetchByAlbumIds(salf.payload.map(al => al.id))

      default:
        return this.fetchAll()
    }
  }

  fetchAll(): Observable<PlaylistsData> {
    return this.http.get(this.fetchUrl)
      .pipe(
        map(res => {
          const playlistsRes: PlaylistsGetAll = res as PlaylistsGetAll
          const { count, playlists }: PlaylistsData = playlistsRes.data
          return { count, playlists }
        })
      )
  }

  fetchByArtistIds(artistIds: number[]): Observable<PlaylistsByArtistIds> {
    return this.http.post(this.fetchUrl + "/byArtistIds", { artistIds })
      .pipe(
        map( res => {
          const playlistRes: PlaylistsGetByArtistIds = res as PlaylistsGetByArtistIds
          const { count, playlists, artistIds }: PlaylistsByArtistIds = playlistRes.data
          return { count, playlists, artistIds}
        })
      )
  }

  fetchByAlbumIds(albumIds: number[]): Observable<PlaylistsByAlbumIds> {
    return this.http.post(this.fetchUrl + "/byAlbumIds", { albumIds })
      .pipe(
        map(res => {
          const playlistRes: PlaylistsGetByAlbumIds = res as PlaylistsGetByAlbumIds
          const { count, playlists, albumIds }: PlaylistsByAlbumIds = playlistRes.data
          return { count, playlists, albumIds }
        })
      )
  }

  create(playlist: Playlist): Observable<any> {
    return this.http.post(this.crudUrl, {name: playlist.name, trackIds: playlist.tracks.map(t => t.id) })
  }

  update(dto: PlaylistUpdateDto): Observable<any> {
    return this.http.put(this.crudUrl + `/${dto.id}`, dto)
  }
}
