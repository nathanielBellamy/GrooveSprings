import {Component, Injectable} from '@angular/core'
import {TransportControlComponent} from "./components/transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./components/playbackDisplay/playbackDisplay.component";

@Component({
  selector: 'gsPlayback',
  templateUrl: './playback.component.html',
  styleUrl: './playback.component.sass'
})
@Injectable()
export class PlaybackComponent {

  constructor() { }

}
