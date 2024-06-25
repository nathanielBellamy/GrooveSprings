import {PlaylistComponent} from "./playlist.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [PlaylistComponent],
    imports: [
        AsyncPipe,
        NgForOf,
        FormsModule,
        ReactiveFormsModule
    ],
  exports: [PlaylistComponent]
})
export class PlaylistModule {}
