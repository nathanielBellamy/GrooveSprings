import {TransportControlComponent} from "./transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./playbackDisplay/playbackDisplay.component";
import {NgModule} from "@angular/core";
import {PlaybackComponent} from "./playback.component";

@NgModule({
  declarations: [PlaybackComponent],
  exports: [PlaybackComponent],
  imports: [TransportControlComponent, PlaybackDisplayComponent]
})
export class PlaybackModule {}
