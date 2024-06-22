import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { map, mapTo } from 'rxjs';
import {ArtistsGetAll} from "../../../models/artists/artists_get_all.model";
import {Artist} from "../../../models/artists/artist.model";
import {ArtistsGetByAlbumIds} from "../../../models/artists/artists_get_by_album_ids.model";
import {ArtistsByAlbumIds} from "../../../models/artists/artists_by_album_ids.model";
import {ArtistsGetByPlaylistIds} from "../../../models/artists/artists_get_by_playlist_ids.model";
import {ArtistsByPlaylistIds} from "../../../models/artists/artists_by_playlist_ids.model";

@Injectable()
export class ArtistsService {
  constructor(private http: HttpClient) { }

  fetchAll() {
    return this.http.get(`api/v1/artists`)
      .pipe(
        map(res => {
          const artistsRes: ArtistsGetAll = res as ArtistsGetAll
          const { count, artists } = artistsRes.data
          artists.sort((a,b) => this.artistNameSort(a,b)) // alphabetize artists ignoring "The "

          return {count, artists}
        })
      )
  }

  fetchByAlbumIds(albumIds: number[]) {
    return this.http.post('api/v1/artists/byAlbumIds', {albumIds})
      .pipe(
        map(res => {
          const artistsRes = res as ArtistsGetByAlbumIds
          const { count, artists, albumIds }: ArtistsByAlbumIds = artistsRes.data
          artists.sort((a,b) => this.artistNameSort(a,b))
          return {count, artists, albumIds}
        })
      )
  }

  fetchByPlaylistIds(playlistIds: number[]) {
    return this.http.post('api/v1/artists/byPlaylistIds', {playlistIds})
      .pipe(
        map( res => {
          const artistsRes = res as ArtistsGetByPlaylistIds
          const { count, artists, playlistIds }: ArtistsByPlaylistIds = artistsRes.data
          artists.sort((a,b) => this.artistNameSort(a,b))
          return {count, artists, playlistIds}
        })
      )
  }

  artistNameSort(a: Artist, b: Artist): number {
    return this.trimArtistName(a.name).localeCompare(this.trimArtistName(b.name))
  }

  trimArtistName(artistName: string): string {
    var res: string = artistName
    if (artistName.substring(0, 4) == "The ") {
      res = artistName.replace("The ", '')
    }
    return res
  }
}
