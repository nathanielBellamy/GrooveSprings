import {NgModule} from "@angular/core";
import {LibraryComponent} from "./library.component";
import {StoreModule} from "@ngrx/store";
import {libraryReducer} from "../../store/library.reducer";
import {EffectsModule} from "@ngrx/effects";
import {libraryEffects} from "../../store/library.effects";
import {ArtistsModule} from "../artists/artists.module";
import {ArtistsService} from "../../services/artists.service";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {AlbumsModule} from "../albums/albums.module";
import {TracksModule} from "../tracks/tracks.module";
import {LibraryService} from "../../services/library.service";
import {PlaylistModule} from "../../../playback/components/playlist/playlist.module";


@NgModule({
  declarations: [LibraryComponent],
  exports: [LibraryComponent],
    imports: [
        AlbumsModule,
        ArtistsModule,
        TracksModule,
        StoreModule.forFeature('library', libraryReducer),
        EffectsModule.forFeature(libraryEffects),
        AsyncPipe,
        NgForOf,
        NgIf,
        PlaylistModule,
    ],
  providers: [
    ArtistsService,
    LibraryService
  ]
})
export class LibraryModule {}
