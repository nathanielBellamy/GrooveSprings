import {NgModule} from "@angular/core";
import {PlaybackDisplayComponent} from "./playbackDisplay.component";
import {AsyncPipe, NgForOf, NgStyle} from "@angular/common";

@NgModule({
  declarations: [PlaybackDisplayComponent],
    imports: [
        AsyncPipe,
        NgStyle
    ],
  exports: [PlaybackDisplayComponent]
})
export class PlaybackDisplayModule {}
