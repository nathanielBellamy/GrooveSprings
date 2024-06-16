import {NgModule} from "@angular/core";
import {PlaybackDisplayComponent} from "./playbackDisplay.component";
import {AsyncPipe, NgForOf} from "@angular/common";

@NgModule({
  declarations: [PlaybackDisplayComponent],
  imports: [
    AsyncPipe
  ],
  exports: [PlaybackDisplayComponent]
})
export class PlaybackDisplayModule {}
