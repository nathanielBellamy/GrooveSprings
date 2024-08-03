import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { faPlay, faPause, faStop, faForwardFast, faBackward } from '@fortawesome/free-solid-svg-icons'
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {
  NextTrackTrig,
  PauseTrig,
  PlaybackSpeedTrig,
  PlayTrig,
  PrevTrackTrig,
  StopTrig
} from "../../store/playback.actions";
import {GsPlaybackSpeed} from "../../../../enums/gsPlaybackSpeed.enum";
import {Observable} from "rxjs";
import {GsPlayState} from "../../../../enums/gsPlayState.enum";

@Component({
  selector: 'gsTransportControl',
  templateUrl: './transportControl.component.html',
  styleUrl: './transportControl.component.sass'
})
@Injectable()
export class TransportControlComponent {
  protected gsPlaybackSpeedOptions: number[] = [GsPlaybackSpeed._N2, GsPlaybackSpeed._N1, GsPlaybackSpeed._N05, GsPlaybackSpeed._05, GsPlaybackSpeed._1, GsPlaybackSpeed._2]
  protected faPlay = faPlay
  protected faPause = faPause
  protected faStop = faStop
  protected faForwardFast = faForwardFast
  protected faBackward = faBackward

  protected playState$: Observable<GsPlayState>
  protected playbackSpeed$: Observable<GsPlaybackSpeed>

  constructor(private store$: Store<{playback: PlaybackState}>) {
    this.playState$ = store$.select(state => state.playback.playState)
    this.playbackSpeed$ = store$.select(state => state.playback.playbackSpeed)
  }

  playTrig() {
    this.store$.dispatch(new PlayTrig())
  }

  pauseTrig() {
    this.store$.dispatch(new PauseTrig())
  }

  nextTrackTrig() {
    this.store$.dispatch(new NextTrackTrig())
  }

  prevTrackTrig() {
    this.store$.dispatch(new PrevTrackTrig())
  }

  stopTrig() {
    this.store$.dispatch(new StopTrig())
  }

  handlePlaybackSpeed(newIdx: any) {
    const speed = this.gsPlaybackSpeedOptions[newIdx]
    this.store$.dispatch(new PlaybackSpeedTrig(speed))
  }

  protected readonly GsPlaybackSpeed = GsPlaybackSpeed;
  protected readonly GsPlayState = GsPlayState;
}
