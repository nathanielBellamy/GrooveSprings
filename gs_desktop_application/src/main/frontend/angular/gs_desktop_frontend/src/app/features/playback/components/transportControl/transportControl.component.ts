import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AsyncPipe} from "@angular/common";

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
  private socket = this.getSocket()
  protected gsPlaybackSpeedOptions: number[] = [-1.0, 1.0]

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.socket.onopen = () => console.log('gs-transport-control opened')
    this.socket.onmessage = () => {}
    this.socket.onclose = () => {
      this.socket.close()
      console.log('gs-transport-control closed')
      this.socket = this.getSocket();
    }
    this.socket.onerror = (e) => {
      this.socket.close()
      console.log(e)
      this.socket = this.getSocket();
    }
  }

  ngOnDestroy() {
    this.socket.close()
  }

  getSocket() {
    return new WebSocket('ws://localhost:8766/gs-transport-control')
  }

  playTrig() {
    this.socket.send('play')
  }

  pauseTrig() {
    this.socket.send('pause')
  }

  stopTrig() {
    this.socket.send('stop')
  }

  handlePlaybackSpeed(newIdx: any) {
    const speed = this.gsPlaybackSpeedOptions[newIdx]
    console.dir(speed)
    this.socket.send(`${speed}`)
  }
}
