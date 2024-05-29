import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { map, mapTo } from 'rxjs';
import {ArtistsGetAll} from "../../../models/artists/artists_get_all.model";
import {Artist} from "../../../models/artists/artist.model";

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
