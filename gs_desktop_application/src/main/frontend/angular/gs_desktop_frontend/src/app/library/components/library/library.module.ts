import {NgModule} from "@angular/core";
import {LibraryComponent} from "./library.component";
import {StoreModule} from "@ngrx/store";
import {libraryReducer} from "../../store/library.reducer";
import {EffectsModule} from "@ngrx/effects";
import {libraryEffects} from "../../store/library.effects";
import {ArtistsModule} from "../artists/artists.module";
import {ArtistsService} from "../../services/artists.service";
import {AsyncPipe, NgForOf} from "@angular/common";
import {AlbumsModule} from "../albums/albums.module";
import {TracksModule} from "../tracks/tracks.module";


@NgModule({
  declarations: [LibraryComponent],
  exports: [LibraryComponent],
  imports: [
    AlbumsModule,
    ArtistsModule,
    TracksModule,
    StoreModule.forFeature('libraryStore', libraryReducer),
    EffectsModule.forFeature(libraryEffects),
    AsyncPipe,
    NgForOf,
  ],
  providers: [
    ArtistsService
  ]
})
export class LibraryModule {}
