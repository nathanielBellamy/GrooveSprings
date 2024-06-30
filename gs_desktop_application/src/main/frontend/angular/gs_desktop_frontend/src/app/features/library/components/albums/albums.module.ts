import {AlbumsComponent} from "./albums.component";
import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {AlbumsService} from "../../services/albums.service";
import {gsPipes} from "../../../../pipes/gsPipes.pipe";

@NgModule({
  declarations: [AlbumsComponent],
  exports: [AlbumsComponent],
  providers: [AlbumsService],
  imports: [
    NgForOf,
    AsyncPipe,
    gsPipes
  ],
})
export class AlbumsModule {}
