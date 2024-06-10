import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'gsTransportControl',
  standalone: true,
  templateUrl: './transportControl.component.html',
  styleUrl: './transportControl.component.sass'
})
@Injectable()
export class TransportControlComponent {
  private socket = this.getSocket()

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
}
