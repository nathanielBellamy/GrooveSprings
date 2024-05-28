import {ArtistsComponent} from "./artists.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {ArtistService} from "../../services/artists.service";
import {LibraryModule} from "../library/library.module";

@NgModule({
  declarations: [ArtistsComponent],
  exports: [ArtistsComponent],
  providers: [ArtistService],
  imports: [
    NgForOf,
    AsyncPipe
  ],
})
export class ArtistsModule {}
