import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {TracksGetAll} from "../../../models/tracks/tracks_get_all.model";
import {TracksData} from "../../../models/tracks/tracks_data.model";
import {TracksGetByArtistIds} from "../../../models/tracks/tracks_get_by_artist_ids.model";
import {TracksByArtistIds} from "../../../models/tracks/tracks_by_artist_ids.model";
import {ArtistsGetByAlbumIds} from "../../../models/artists/artists_get_by_album_ids.model";
import {ArtistsByAlbumIds} from "../../../models/artists/artists_by_album_ids.model";
import {TracksGetByAlbumIds} from "../../../models/tracks/tracks_get_by_album_ids.model";
import {TracksByAlbumIds} from "../../../models/tracks/tracks_by_album_ids.model";
import {TracksByPlaylistIds} from "../../../models/tracks/tracks_by_playlist_ids.model";
import {TracksGetByPlaylistIds} from "../../../models/tracks/tracks_get_by_playlist_ids.model";
import {Action} from "@ngrx/store";
import {LibraryActionTypes} from "../store/library.actiontypes";
import {SetAlbumsFilter, SetArtistsFilter, SetPlaylistsFilter} from "../store/library.actions";
import {Track} from "../../../models/tracks/track.model";

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

  fetchByAction(action: Action): Observable<TracksByPlaylistIds | TracksByAlbumIds | TracksByArtistIds> {
    switch (action.type) {
      case LibraryActionTypes.SetPlaylistsFilter:
        const splf = action as SetPlaylistsFilter
        return this.fetchByPlaylistIds(splf.payload.map(pl => pl.id), splf)
      case LibraryActionTypes.SetAlbumsFilter:
        const salf = action as SetAlbumsFilter
        return this.fetchByAlbumIds(salf.payload.map(al => al.id))
      default: // case LibraryActionTypes.SetArtistsFilter:
        const sarf = action as SetArtistsFilter
        return this.fetchByArtistIds(sarf.payload.map(ar => ar.id))
    }
  }

  fetchByPlaylistIds(playlistIds: number[], splf: SetPlaylistsFilter): Observable<TracksByPlaylistIds> {
    return this.http.post(`api/v1/tracks/byPlaylistIds`, { playlistIds })
      .pipe(
        map( res => {
          // TODO:
          // - at the moment, we assume just 1 Playlist was loaded (this is all the UI allows)
          // - the same track may appear on multiple playlists
          // - q: do we want to duplicate the track in the track results?
          const tracksRes: TracksGetByPlaylistIds = res as TracksGetByPlaylistIds
          tracksRes.data.tracks.sort((a,b) => {
            const aIndex = splf.payload[0].trackIds.indexOf(a.id)
            const bIndex = splf.payload[0].trackIds.indexOf(b.id)
            return aIndex - bIndex
          })
          return tracksRes.data
        })
      )
  }

  fetchByArtistIds(artistIds: number[]): Observable<TracksByArtistIds> {
    return this.http.post(`api/v1/tracks/byArtistIds`, { artistIds })
      .pipe(
        map(res => {
          const tracksRes: TracksGetByArtistIds = res as TracksGetByArtistIds
          tracksRes.data.tracks.sort(this.sortTracksByAlbum)
          return tracksRes.data
        })
      )
  }

  fetchByAlbumIds(albumIds: number[]) {
    return this.http.post('api/v1/tracks/byAlbumIds', {albumIds})
      .pipe(
        map(res => {
          const artistsRes = res as TracksGetByAlbumIds
          const { count, tracks, albumIds }: TracksByAlbumIds = artistsRes.data
          tracks.sort(this.sortTracksByAlbum)
          return {count, tracks, albumIds}
        })
      )
  }

  sortTracksByAlbum(a: Track, b: Track): number {
    if (a.album.id == b.album.id) {
      return a.trackNumber - b.trackNumber
    } else {
      return a.album.title.localeCompare(b.album.title)
    }
  }
}
