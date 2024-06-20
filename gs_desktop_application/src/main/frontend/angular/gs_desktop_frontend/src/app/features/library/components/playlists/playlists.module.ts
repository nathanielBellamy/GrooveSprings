import {PlaylistsComponent} from "./playlists.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {PlaylistsService} from "../../services/playlists.service";

@NgModule({
  declarations: [PlaylistsComponent],
  exports: [PlaylistsComponent],
  providers: [PlaylistsService],
  imports: [
    NgForOf,
    AsyncPipe
  ],
})
export class PlaylistsModule {}
