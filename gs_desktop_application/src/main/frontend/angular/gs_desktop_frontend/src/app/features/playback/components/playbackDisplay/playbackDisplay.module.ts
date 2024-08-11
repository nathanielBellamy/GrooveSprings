import {NgModule} from "@angular/core";
import {PlaybackDisplayComponent} from "./playbackDisplay.component";
import {AsyncPipe, NgForOf, NgStyle} from "@angular/common";
import {gsPipes} from "../../../../pipes/gsPipes.pipe";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@NgModule({
  declarations: [PlaybackDisplayComponent],
    imports: [
        AsyncPipe,
        NgStyle,
        gsPipes,
        FaIconComponent
    ],
  exports: [PlaybackDisplayComponent]
})
export class PlaybackDisplayModule {}
