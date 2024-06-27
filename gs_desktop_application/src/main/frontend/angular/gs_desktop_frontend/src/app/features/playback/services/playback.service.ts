import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../../../models/tracks/track.model";
import {Playlist} from "../../../models/playlist/playlist.model";
import {PauseTrig, PlaybackSpeedTrig, PlayTrig, StopTrig} from "../store/playback.actions";
import {PlaybackActionTypes} from "../store/playback.actiontypes";
import {PlaybackState} from "../store/playback.state";
import {Store} from "@ngrx/store";

@Injectable()
export class PlaybackService {
  private state: PlaybackState | Object = {}
  constructor(private http: HttpClient, private store$: Store<{playback: PlaybackState}>) {
    store$.subscribe((state) => this.state = {...state.playback})
  }

  setCurrFile(track: Track): Observable<any> {
    const { path } = track
    return this.http.put("api/v1/file-select", { path, stateJson: JSON.stringify(this.state)}, {responseType: 'text'})
  }

  cacheState(): Observable<any> {
    return this.http.put("api/v1/cacheState", {stateJson: JSON.stringify(this.state)}, {responseType: 'text'})
  }

  fetchPlaylistTracks(playlist: Playlist): Observable<any> {
    return this.http.get("api/v1/playlists/" + playlist.id + "/tracks")
  }

  fetchLastState(): Observable<any> {
    return this.http.get("api/v1/lastState", {responseType: 'text'})
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
