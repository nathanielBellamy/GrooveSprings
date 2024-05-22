import { Component  } from '@angular/core'
import {TransportControlComponent} from "./transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./playbackDisplay/playbackDisplay.component";

@Component({
  selector: 'gsPlayback',
  standalone: true,
  imports: [TransportControlComponent, PlaybackDisplayComponent],
  templateUrl: './playback.component.html',
  styleUrl: './playback.component.sass'
})
export class PlaybackComponent {

  constructor() { }

}
