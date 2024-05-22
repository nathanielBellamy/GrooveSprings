import { Component  } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'gsTransportControl',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './transportControl.component.html',
  styleUrl: './transportControl.component.sass'
})
@Injectable()
export class TransportControlComponent {
  private socket = new WebSocket('ws://localhost:8766/gs-transport-control')

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.socket.onopen = () => console.log('gs-transport-control opened')
    this.socket.onmessage = () => {}
    this.socket.onclose = () => console.log('gs-transport-control closed')
    this.socket.onerror = (e) => console.log(e)
  }

  ngOnDestroy() {
    this.socket.close()
  }

  playTrig() {
    this.socket.send('play')
  }

  stopTrig() {
    this.socket.send('stop')
  }
}
