import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AsyncPipe} from "@angular/common";
import {PlaybackDisplayModule} from "../playbackDisplay/playbackDisplay.module"
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome'
import { faPlay, faPause, faStop, faForwardFast, faBackward } from '@fortawesome/free-solid-svg-icons'

@Component({
  selector: 'gsTransportControl',
  standalone: true,
  templateUrl: './transportControl.component.html',
  imports: [
    AsyncPipe,
    PlaybackDisplayModule,
    FontAwesomeModule
  ],
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

  constructor(private http: HttpClient) { }

  playTrig() {
    this.http.get('api/v1/transport/play', {responseType: 'text'}).subscribe()
  }

  pauseTrig() {
    this.http.get('api/v1/transport/pause', {responseType: 'text'}).subscribe()
  }

  stopTrig() {
    this.http.get('api/v1/transport/stop', {responseType: 'text'}).subscribe()
  }

  handlePlaybackSpeed(newIdx: any) {
    const speed = this.gsPlaybackSpeedOptions[newIdx]
    this.http.post('api/v1/transport/playbackSpeed', { speed }, {responseType: 'text'}).subscribe()
  }
}
