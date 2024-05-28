// app.module.ts
//
import {EffectsModule, EffectsRootModule, provideEffects} from "@ngrx/effects";
import {importProvidersFrom, NgModule} from "@angular/core";
import {provideStore, StoreModule, StoreRootModule} from "@ngrx/store";
import {provideRouter, RouterModule, RouterOutlet} from "@angular/router";
import {AppComponent} from "./app.component";
import {LibraryModule} from "./library/components/library/library.module";
import {PlaybackModule} from "./playback/playback.module";
import {routes} from "./app.routes";
import {BrowserModule} from "@angular/platform-browser";
import {ArtistService} from "./library/services/artists.service";
import {HttpClientModule} from "@angular/common/http";
import {libraryReducer} from "./library/store/library.reducer";
import {LibraryEffects} from "./library/store/library.effects";

@NgModule({
  declarations: [AppComponent],
  imports: [
    StoreModule.forRoot({}),
    EffectsModule.forRoot({}),
    RouterModule.forRoot(routes),
    BrowserModule,
    RouterOutlet,
    LibraryModule,
    PlaybackModule,
  ],
  providers: [
    ArtistService,
    provideRouter(routes),
    importProvidersFrom(
      HttpClientModule,
      StoreRootModule,
      EffectsRootModule,
      LibraryModule
    ),
    provideStore({
      library: libraryReducer
    }),
    provideEffects([LibraryEffects]),
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
