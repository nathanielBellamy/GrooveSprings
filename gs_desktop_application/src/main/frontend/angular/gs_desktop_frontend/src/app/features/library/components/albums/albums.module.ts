import {AlbumsComponent} from "./albums.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {AlbumsService} from "../../services/albums.service";

@NgModule({
  declarations: [AlbumsComponent],
  exports: [AlbumsComponent],
  providers: [AlbumsService],
  imports: [
    NgForOf,
    AsyncPipe
  ],
})
export class AlbumsModule {}
