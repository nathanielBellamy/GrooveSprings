import {TracksComponent} from "./tracks.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {TracksService} from "../../services/tracks.service";

@NgModule({
  declarations: [TracksComponent],
  exports: [TracksComponent],
  providers: [TracksService],
  imports: [
    NgForOf,
    AsyncPipe
  ],
})
export class TracksModule {}
