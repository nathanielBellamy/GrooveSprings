import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
import {AlbumsGetAll} from "../../models/albums/albums_get_all.model";
import {AlbumsData} from "../../models/albums/albums_data.model";

@Injectable()
export class AlbumsService {
  constructor(private http: HttpClient) { }

  fetchAll() {
    return this.http.get(`api/v1/albums`)
      .pipe(
        map(res => {
          const albumsRes: AlbumsGetAll = res as AlbumsGetAll
          const { count, albums }: AlbumsData  = albumsRes.data
          return { count, albums }
        })
      )
  }

}
