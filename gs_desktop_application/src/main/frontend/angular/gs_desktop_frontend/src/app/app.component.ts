import { Component  } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import {webSocket} from 'rxjs/webSocket'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass'
})
export class AppComponent {
  title = 'gs_desktop_frontend'
  private socket = new WebSocket('ws://localhost:8766/gs-transport-control')
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