import {PlaylistComponent} from "./playlist.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [PlaylistComponent],
    imports: [
        AsyncPipe,
        NgForOf,
        FormsModule
    ],
  exports: [PlaylistComponent]
})
export class PlaylistModule {}
