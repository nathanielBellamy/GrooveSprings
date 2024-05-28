import {Component, Injectable} from '@angular/core'
import {TransportControlComponent} from "./transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./playbackDisplay/playbackDisplay.component";

@Component({
  selector: 'gsPlayback',
  templateUrl: './playback.component.html',
  styleUrl: './playback.component.sass'
})
@Injectable()
export class PlaybackComponent {

  constructor() { }

}
