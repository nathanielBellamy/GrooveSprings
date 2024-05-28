import {ArtistsComponent} from "./artists.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {ArtistsService} from "../../services/artists.service";

@NgModule({
  declarations: [ArtistsComponent],
  exports: [ArtistsComponent],
  providers: [ArtistsService],
  imports: [
    NgForOf,
    AsyncPipe
  ],
})
export class ArtistsModule {}
