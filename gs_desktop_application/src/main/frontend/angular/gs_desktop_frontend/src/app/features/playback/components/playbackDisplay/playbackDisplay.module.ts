import {NgModule} from "@angular/core";
import {PlaybackDisplayComponent} from "./playbackDisplay.component";
import {AsyncPipe, NgForOf, NgStyle} from "@angular/common";
import {gsPipes} from "../../../../pipes/gsPipes.pipe";

@NgModule({
  declarations: [PlaybackDisplayComponent],
    imports: [
        AsyncPipe,
        NgStyle,
        gsPipes
    ],
  exports: [PlaybackDisplayComponent]
})
export class PlaybackDisplayModule {}
