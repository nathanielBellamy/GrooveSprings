import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AsyncPipe} from "@angular/common";
import {PlaybackDisplayModule} from "../playbackDisplay/playbackDisplay.module"
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome'
import { faPlay, faPause, faStop, faForwardFast, faBackward } from '@fortawesome/free-solid-svg-icons'
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {PauseTrig, PlaybackSpeedTrig, PlayTrig, StopTrig} from "../../store/playback.actions";

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

  stopTrig() {
    this.store$.dispatch(new StopTrig())
  }

  handlePlaybackSpeed(newIdx: any) {
    const speed = this.gsPlaybackSpeedOptions[newIdx]
    this.store$.dispatch(new PlaybackSpeedTrig(speed))
  }
}
