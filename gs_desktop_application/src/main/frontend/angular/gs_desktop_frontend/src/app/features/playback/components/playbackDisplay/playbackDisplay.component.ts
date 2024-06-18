import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {Track} from "../../../../models/tracks/track.model";
import {map, Observable} from "rxjs";
import { webSocket } from "rxjs/webSocket";
import {WebSocketSubject} from "rxjs/internal/observable/dom/WebSocketSubject";

@Component({
  selector: 'gsPlaybackDisplay',
  templateUrl: './playbackDisplay.component.html',
  styleUrl: './playbackDisplay.component.sass'
})
@Injectable()
export class PlaybackDisplayComponent {
  private wsSubject: WebSocketSubject<unknown> = this.getWsSubject()
  protected currTrackSfFrames: number = 100
  protected currTrackSfChannels: number = 2
  protected currTrack$: Observable<Track>
  protected currPercent: number = 0

  constructor(private http: HttpClient, private store$: Store<{playback: PlaybackState}>) {
    this.currTrack$ = store$.select(state => ({...state.playback.playlist.tracks[state.playback.currPlaylistTrackIdx]}))
    this.currTrack$.subscribe(track => {
        this.currTrackSfFrames = track.sf_frames
        this.currTrackSfChannels = track.sf_channels
      }
    )
  }

  getWsSubject(): WebSocketSubject<unknown> {
    const subject = webSocket('ws://localhost:8767/gs-display')

    subject.subscribe({
      next: (msg: unknown) => {
        const msgNum: number = typeof msg === 'number' ? msg : 0
        this.setCurrPercent(msgNum)

      },
      error: (e: any) => console.error({playbackDisplaySocketError: e}),
      complete: () => this.wsSubject = this.getWsSubject()
    })

    return subject
  }

  getCurrPercent(lastFrameId: number) {
    return Math.round(100 * lastFrameId / (this.currTrackSfFrames * this.currTrackSfChannels))
  }

  setCurrPercent(lastFrameId: number) {
    this.currPercent = this.getCurrPercent(lastFrameId)
  }
  protected readonly JSON = JSON;
}
