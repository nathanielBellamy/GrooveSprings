import {PlaylistComponent} from "./playlist.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {gsPipes} from "../../../../pipes/gsPipes.pipe";

@NgModule({
  declarations: [PlaylistComponent],
    imports: [
        AsyncPipe,
        NgForOf,
        FormsModule,
        ReactiveFormsModule,
        gsPipes
    ],
  exports: [PlaylistComponent]
})
export class PlaylistModule {}
