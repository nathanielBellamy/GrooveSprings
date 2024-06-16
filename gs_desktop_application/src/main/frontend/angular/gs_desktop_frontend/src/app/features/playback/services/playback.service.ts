import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../../../models/tracks/track.model";

@Injectable()
export class PlaybackService {
  constructor(private http: HttpClient) {}

  setCurrFile(track: Track): Observable<any> {
    // TODO: make into a POST, put path in body to avoid formatting issues with query string
    const { path } = track
    return this.http.put("api/v1/file-select", { path }, {responseType: 'text'})
  }
}
