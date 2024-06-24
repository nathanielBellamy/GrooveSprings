import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {defaultTrack, Track} from "../../../../models/tracks/track.model";
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
  protected currTrack$: Observable<Track>
  protected currTrack: Track = defaultTrack
  protected currTrackArtists: string = "-"
  protected currPercent: number = 0

  constructor(private http: HttpClient, private store$: Store<{playback: PlaybackState}>) {
    this.currTrack$ = store$.select(state => ({...state.playback.currTrack}))
    this.currTrack$.subscribe(track => {
      this.currTrackArtists = track.artists.length ? track.artists.map(a => a.name).join(', ') : "-"
      this.currTrack = {...track}
    })
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
    const denominator = this.currTrack.sf_frames * this.currTrack.sf_channels
    const validDenominator = typeof denominator === 'number' && denominator != 0 && !isNaN(denominator) ? denominator : 1
    return Math.round(100 * lastFrameId / validDenominator)
  }

  setCurrPercent(lastFrameId: number) {
    this.currPercent = this.getCurrPercent(lastFrameId)
  }
  protected readonly JSON = JSON;
}
