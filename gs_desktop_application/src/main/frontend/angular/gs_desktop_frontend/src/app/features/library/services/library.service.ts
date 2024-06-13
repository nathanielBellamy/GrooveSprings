import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AlbumsData} from "../../../models/albums/albums_data.model";

@Injectable()
export class LibraryService {
  constructor(private http: HttpClient) {}

  runScan(): Observable<any> {
    return this.http.post('api/v1/scan', {})
  }
}
