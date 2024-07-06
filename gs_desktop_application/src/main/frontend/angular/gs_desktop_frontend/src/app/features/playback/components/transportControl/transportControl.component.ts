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

@Component({
  selector: 'gsTransportControl',
  templateUrl: './transportControl.component.html',
  styleUrl: './transportControl.component.sass'
})
@Injectable()
export class TransportControlComponent {
  protected gsPlaybackSpeedOptions: number[] = [-2.0, -1.0, -0.5, 0.5, 1.0, 2.0]
  protected faPlay = faPlay
  protected faPause = faPause
  protected faStop = faStop
  protected faForwardFast = faForwardFast
  protected faBackward = faBackward

  constructor(private store$: Store<{playback: PlaybackState}>) { }

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
}
