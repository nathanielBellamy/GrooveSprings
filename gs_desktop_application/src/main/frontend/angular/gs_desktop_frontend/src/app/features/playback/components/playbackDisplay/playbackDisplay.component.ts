import {Component, Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http';
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {defaultTrack, Track} from "../../../../models/tracks/track.model";
import {faRotate, faShuffle} from '@fortawesome/free-solid-svg-icons'
import {Observable} from "rxjs";
import {webSocket} from "rxjs/webSocket";
import {WebSocketSubject} from "rxjs/internal/observable/dom/WebSocketSubject";
import {FetchAppState, HydrateAppState, SetLoopType, ToggleShuffle} from "../../store/playback.actions";
import {playbackStateFromPlaybackStateSrvr, PlaybackStateSrvr} from "../../../../models/srvr/playbackState.srvr.model";
import {GsPlayState} from "../../../../enums/gsPlayState.enum";
import {GsLoopType} from "../../../../enums/gsLoopType.enum";

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
  protected loopType: GsLoopType = GsLoopType.NONE
  protected faRotate = faRotate
  protected faShuffle = faShuffle

  // ping ws to keep alive
  private pingIntervalId: number = 0 // setInterval returns non-zero number
  private pingInterval: number = 30 * 1000 // every 30 seconds

  constructor(private http: HttpClient, private store$: Store<{playback: PlaybackState}>) {
    this.currTrack$ = store$.select(state => ({...state.playback.currTrack}))
    this.currTrack$.subscribe(track => {
      this.currTrackArtists = track.artists.length ? track.artists.map(a => a.name).join(', ') : "-"
      this.currTrack = {...track}
    })

    store$
      .select(state => state.playback.loopType)
      .subscribe(val => this.loopType = val)

    store$
      .select(state => state.playback.currFrameId)
      .subscribe(val => this.currPercent = this.getCurrPercent(val))
  }

  ngOnInit() {
    this.pingIntervalId = setInterval(() => this.pingSocket(), this.pingInterval)
  }

  ngOnDestroy() {
    if (this.pingIntervalId) clearInterval(this.pingIntervalId)
    this.wsSubject.unsubscribe()
  }

  pingSocket() {
    this.wsSubject.next('ping')
  }

  getWsSubject(): WebSocketSubject<unknown> {
    const subject = webSocket({
      url: 'ws://localhost:8767/gs-display',
      openObserver: {
        next: () => this.store$.dispatch(new FetchAppState())
      }
    })

    // TODO: multiplex
    subject
      .subscribe({
        next: (msg: unknown) => {
          let newAppState: PlaybackState | null = null
          if (typeof msg !== 'number') { // then it is state update
            try {
              newAppState = playbackStateFromPlaybackStateSrvr(msg as PlaybackStateSrvr)
              this.store$.dispatch(new HydrateAppState(newAppState))
            } catch(e) {
              console.error(e)
            }
          }
          if (typeof msg === 'number' && msg > -1) {
            this.setCurrPercent(msg)
            return
          } else if (newAppState && newAppState.playState === GsPlayState.STOP) {
            this.setCurrPercent(0)
            return
          }
        },
        error: (e: any) => console.error({playbackDisplaySocketError: e}),
        complete: () => this.wsSubject = this.getWsSubject()
    })

    return subject
  }

  getCurrPercent(lastFrameId: number): number {
    const denominator = this.currTrack.sf_frames * this.currTrack.sf_channels
    const validDenominator = typeof denominator === 'number' && denominator != 0 && !isNaN(denominator) ? denominator : 1
    return Math.round(100 * lastFrameId / validDenominator)
  }

  setCurrPercent(lastFrameId: number): void {
    this.currPercent = this.getCurrPercent(lastFrameId)
  }

  handleLoopTypeClick(): void {
    this.store$.dispatch(new SetLoopType())
  }

  handleShuffleClick(): void {
    this.store$.dispatch(new ToggleShuffle())
  }
}
