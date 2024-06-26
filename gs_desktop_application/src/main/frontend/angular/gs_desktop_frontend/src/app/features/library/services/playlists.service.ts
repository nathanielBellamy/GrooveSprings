import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {PlaylistsGetAll} from "../../../models/playlist/playlists_get_all.model";
import {PlaylistsData} from "../../../models/playlist/playlists_data.model";
import {Playlist} from "../../../models/playlist/playlist.model";
import {PlaylistCreateRes} from "../../../models/playlist/playlist_create_res.model";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../store/library.actiontypes";
import {SetAlbumsFilter, SetArtistsFilter} from "../store/library.actions";
import {PlaylistsByArtistIds} from "../../../models/playlist/playlists_by_artist_ids.model";
import {PlaylistsGetByArtistIds} from "../../../models/playlist/playlists_get_by_artist_ids.model";
import {PlaylistsByAlbumIds} from "../../../models/playlist/playlists_by_album_ids.model";
import {PlaylistsGetByAlbumIds} from "../../../models/playlist/playlists_get_by_album_ids.model";
import {PlaylistUpdateDto} from "../../../models/playlist/playlist_update_dto.model";
import {PlaylistUpdateRes} from "../../../models/playlist/playlist_update_res.model";

@Injectable()
export class PlaylistsService {
  private url: string = "api/v1/playlists"

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
    return this.http.get(this.url)
      .pipe(
        map(res => {
          const playlistsRes: PlaylistsGetAll = res as PlaylistsGetAll
          const { count, playlists }: PlaylistsData = playlistsRes.data
          return { count, playlists }
        })
      )
  }

  fetchByArtistIds(artistIds: number[]): Observable<PlaylistsByArtistIds> {
    return this.http.post(this.url + "/byArtistIds", { artistIds })
      .pipe(
        map( res => {
          const playlistRes: PlaylistsGetByArtistIds = res as PlaylistsGetByArtistIds
          const { count, playlists, artistIds }: PlaylistsByArtistIds = playlistRes.data
          return { count, playlists, artistIds}
        })
      )
  }

  fetchByAlbumIds(albumIds: number[]): Observable<PlaylistsByAlbumIds> {
    return this.http.post(this.url + "/byAlbumIds", { albumIds })
      .pipe(
        map(res => {
          const playlistRes: PlaylistsGetByAlbumIds = res as PlaylistsGetByAlbumIds
          const { count, playlists, albumIds }: PlaylistsByAlbumIds = playlistRes.data
          return { count, playlists, albumIds }
        })
      )
  }

  create(playlist: Playlist): Observable<Playlist> {
    return this.http.post(this.url, {name: playlist.name, trackIds: playlist.tracks.map(t => t.id) })
      .pipe(
        map(res => {
          const playlistCreate: PlaylistCreateRes = res as PlaylistCreateRes
          return playlistCreate.data.playlist
        })
      )
  }

  update(dto: PlaylistUpdateDto): Observable<Playlist> {
    return this.http.put(this.url + `/${dto.id}`, dto)
      .pipe(
        map(res => {
          const playlistUpdate: PlaylistUpdateRes = res as PlaylistUpdateRes
          return playlistUpdate.data.playlist
        })
      )
  }
}
