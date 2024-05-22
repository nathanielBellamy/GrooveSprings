import { Component  } from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'gsPlaybackDisplay',
  standalone: true,
  templateUrl: './playbackDisplay.component.html',
  styleUrl: './playbackDisplay.component.sass'
})
@Injectable()
export class PlaybackDisplayComponent {
  private socket = new WebSocket('ws://localhost:8767/gs-display')
  protected lastFrameId = "0"

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.socket.onopen = () => console.log('gs-display opened')
    this.socket.onmessage = (e) => { this.lastFrameId = e.data }
    this.socket.onclose = () => console.log('gs-display closed')
    this.socket.onerror = (e) => console.log(e)
  }

  ngOnDestroy() {
    this.socket.close()
  }
}
