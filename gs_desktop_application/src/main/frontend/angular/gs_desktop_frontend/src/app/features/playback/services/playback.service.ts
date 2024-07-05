import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Track} from "../../../models/tracks/track.model";
import {PauseTrig, PlaybackSpeedTrig, PlayTrig, StopTrig} from "../store/playback.actions";
import {PlaybackActionTypes} from "../store/playback.actiontypes";
import {PlaybackState} from "../store/playback.state";
import {Store} from "@ngrx/store";
import {PlaylistRepr} from "../../../models/playlist/playlist_repr.model";
import {playbackStateSrvrFromPlaybackState} from "../../../models/srvr/playbackState.srvr.model";
import {trackSrvrFromTrack} from "../../../models/srvr/tracks.srvr.model";
import {AppRoutesSrvr} from "../../../app.routes.srvr";

@Injectable()
export class PlaybackService {
  private state: PlaybackState | Object = {}
  constructor(private http: HttpClient, private store$: Store<{playback: PlaybackState}>) {
    store$.subscribe((state) => this.state = {...state.playback})
  }

  setCurrFile(track: Track): Observable<any> {
    const { path } = track
    return this.http.put(AppRoutesSrvr.trackSelect(), trackSrvrFromTrack(track), {responseType: 'text'})
  }

  // cacheState(): Observable<any> {
  //   return this.http.put("api/v1/cacheState", playbackStateSrvrFromPlaybackState(this.state as PlaybackState), {responseType: 'text'})
  // }

  fetchPlaylistTracks(playlist: PlaylistRepr): Observable<any> {
    return this.http.get("api/v1/playlists/" + playlist.id + "/tracks")
  }

  fetchAppState(): Observable<any> {
    return this.http.get(AppRoutesSrvr.appState(), {responseType: 'text'})
  }

  transportControlTrig(action: PlayTrig | PauseTrig | StopTrig | PlaybackSpeedTrig): Observable<any> {
    switch (action.type) {
      case PlaybackActionTypes.PlayTrig:
        return this.http.get(AppRoutesSrvr.play(), {responseType: 'text'})
      case PlaybackActionTypes.PauseTrig:
        return this.http.get(AppRoutesSrvr.pause(), {responseType: 'text'})
      case PlaybackActionTypes.PlaybackSpeedTrig:
        const actionT = action as PlaybackSpeedTrig
        return this.http.post(AppRoutesSrvr.playbackSpeed(), { speed: actionT.speed }, {responseType: 'text'})
      default: // PlaybackActionTypes.StopTrig
        return this.http.get(AppRoutesSrvr.stop(), {responseType: 'text'})
    }
  }

  addTrackToPlaylist(track: Track): Observable<any> {
    return this.http.put(AppRoutesSrvr.addTrackToPlaylist(), trackSrvrFromTrack(track), {responseType: 'text'})
  }

  clearPlaylist(): Observable<any> {
    return this.http.delete(AppRoutesSrvr.clearPlaylist(), {responseType: 'text'})
  }
}
