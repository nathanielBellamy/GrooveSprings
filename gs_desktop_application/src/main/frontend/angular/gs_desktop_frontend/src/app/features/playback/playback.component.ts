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
  protected currSection: string = 'transport'

  constructor() { }

  handlePlaybackSectionClick(section: string) {
    this.currSection = section
  }

  get currSectionIsTransport(): boolean {
    return this.currSection === 'transport'
  }

  get currSectionIsPlaylist(): boolean {
    return this.currSection === 'playlist'
  }
}
