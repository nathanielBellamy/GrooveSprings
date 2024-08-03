import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {gsPipes} from "../../../../pipes/gsPipes.pipe";
import {TransportControlComponent} from "./transportControl.component";
import {PlaybackDisplayModule} from "../playbackDisplay/playbackDisplay.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {GsPlaybackSpeedToNumberPipe} from "../../../../pipes/gsPlaybackSpeedToNumber.pipe";

@NgModule({
  declarations: [TransportControlComponent],
  imports: [
    PlaybackDisplayModule,
    gsPipes,
    AsyncPipe,
    FontAwesomeModule,
    NgForOf,
    GsPlaybackSpeedToNumberPipe,
  ],
  exports: [TransportControlComponent]
})
export class TransportControlModule {}
