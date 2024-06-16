import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {Track} from "../../../../models/tracks/track.model";
import {map, Observable} from "rxjs";

@Component({
  selector: 'gsPlaybackDisplay',
  templateUrl: './playbackDisplay.component.html',
  styleUrl: './playbackDisplay.component.sass'
})
@Injectable()
export class PlaybackDisplayComponent {
  private socket = new WebSocket('ws://localhost:8767/gs-display')
  protected lastFrameId = "0"
  protected currTrackSfFrames: number = 100
  protected currTrack$: Observable<Track>
  protected currPercent: number = this.getCurrPercent()

  constructor(private http: HttpClient, private store$: Store<{playback: PlaybackState}>) {
    this.currTrack$ = store$.select(state => ({...state.playback.playlist.tracks[state.playback.currPlaylistTrackIdx]}))
    this.currTrack$.subscribe(track => {
        this.currTrackSfFrames = track.sf_frames
      }
    )
  }

  getCurrPercent() {
    return Math.round(100 * parseInt(this.lastFrameId) / this.currTrackSfFrames);
  }

  setCurrPercent() {
    this.currPercent = this.getCurrPercent()
  }

  ngOnInit() {
    this.socket.onopen = () => console.log('gs-display opened')
    this.socket.onmessage = (e) => {
      this.lastFrameId = e.data
      this.setCurrPercent()
    }
    this.socket.onclose = () => console.log('gs-display closed')
    this.socket.onerror = (e) => console.log(e)
  }

  ngOnDestroy() {
    this.socket.close()
  }

  protected readonly JSON = JSON;
}
