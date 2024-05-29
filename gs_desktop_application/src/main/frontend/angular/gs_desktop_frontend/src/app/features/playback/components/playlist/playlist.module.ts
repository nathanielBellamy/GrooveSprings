import {PlaylistComponent} from "./playlist.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";


@NgModule({
  declarations: [PlaylistComponent],
  imports: [
    AsyncPipe,
    NgForOf
  ],
  exports: [PlaylistComponent]
})
export class PlaylistModule {}
