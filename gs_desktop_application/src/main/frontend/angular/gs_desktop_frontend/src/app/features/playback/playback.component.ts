import {Component, Injectable} from '@angular/core'
import {TransportControlComponent} from "./components/transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./components/playbackDisplay/playbackDisplay.component";
import {FetchLastState} from "./store/playback.actions";
import {PlaybackState} from "./store/playback.state";
import {Store} from "@ngrx/store";

@Component({
  selector: 'gsPlayback',
  templateUrl: './playback.component.html',
  styleUrl: './playback.component.sass'
})
@Injectable()
export class PlaybackComponent {

  constructor(private store$: Store<{playback: PlaybackState}>) { }

  ngOnInit() {
    this.store$.dispatch(new FetchLastState())
  }
}
