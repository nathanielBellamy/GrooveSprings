import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AsyncPipe} from "@angular/common";
import { webSocket } from "rxjs/webSocket";
import {WebSocketSubject} from "rxjs/internal/observable/dom/WebSocketSubject";

@Component({
  selector: 'gsTransportControl',
  standalone: true,
  templateUrl: './transportControl.component.html',
  imports: [
    AsyncPipe
  ],
  styleUrl: './transportControl.component.sass'
})
@Injectable()
export class TransportControlComponent {
  private wsSubject: WebSocketSubject<string> = this.getWsSubject()
  protected gsPlaybackSpeedOptions: number[] = [-2.0, -1.0, -0.5, 0.5, 1.0, 2.0]

  constructor(private http: HttpClient) { }

  getWsSubject() {
    const subject =  webSocket<string>({
      url: 'ws://localhost:8766/gs-transport-control',
      deserializer: (msg: MessageEvent<any>) => msg.data
    })

    subject.subscribe({
      error: (e: any) => console.error({transportControlSocketError: e}),
      complete: () => this.wsSubject = this.getWsSubject()
    })

    return subject
  }

  playTrig() {
    this.wsSubject.next('play')
  }

  pauseTrig() {
    this.wsSubject.next('pause')
  }

  stopTrig() {
    this.wsSubject.next('stop')
  }

  handlePlaybackSpeed(newIdx: any) {
    const speed = this.gsPlaybackSpeedOptions[newIdx]
    this.wsSubject.next(`${speed}`)
  }
}
