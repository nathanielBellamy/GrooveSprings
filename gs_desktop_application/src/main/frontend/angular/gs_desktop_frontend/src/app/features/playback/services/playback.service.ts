import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../../../models/tracks/track.model";
import {Playlist} from "../../../models/playlist/playlist.model";
import {PauseTrig, PlaybackSpeedTrig, PlayTrig, StopTrig} from "../store/playback.actions";
import {PlaybackActionTypes} from "../store/playback.actiontypes";

@Injectable()
export class PlaybackService {
  constructor(private http: HttpClient) {}

  setCurrFile(track: Track): Observable<any> {
    const { path } = track
    return this.http.put("api/v1/file-select", { path, trackJson: JSON.stringify(track) }, {responseType: 'text'})
  }

  fetchPlaylistTracks(playlist: Playlist): Observable<any> {
    return this.http.get("api/v1/playlists/" + playlist.id + "/tracks")
  }

  fetchLastTrack(): Observable<any> {
    return this.http.get("api/v1/lastTrack", {responseType: 'text'})
  }

  transportControlTrig(action: PlayTrig | PauseTrig | StopTrig | PlaybackSpeedTrig): Observable<any> {
    switch (action.type) {
      case PlaybackActionTypes.PlayTrig:
        return this.http.get('api/v1/transport/play', {responseType: 'text'})
      case PlaybackActionTypes.PauseTrig:
        return this.http.get('api/v1/transport/pause', {responseType: 'text'})
      case PlaybackActionTypes.PlaybackSpeedTrig:
        const actionT = action as PlaybackSpeedTrig
        return this.http.post('api/v1/transport/playbackSpeed', { speed: actionT.speed }, {responseType: 'text'})
      default: // PlaybackActionTypes.StopTrig
        return this.http.get('api/v1/transport/stop', {responseType: 'text'})
    }
  }
}
