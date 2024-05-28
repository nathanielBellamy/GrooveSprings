// app.module.ts
//
import {EffectsModule, EffectsRootModule, provideEffects} from "@ngrx/effects";
import {importProvidersFrom, NgModule} from "@angular/core";
import {StoreModule, StoreRootModule} from "@ngrx/store";
// import {provideRouter, RouterModule, RouterOutlet} from "@angular/router";
import {AppComponent} from "./app.component";
import {LibraryModule} from "./library/components/library/library.module";
import {PlaybackModule} from "./playback/playback.module";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {gsState} from "./store/app.state";
import {gsEffects} from "./store/app.effects";

@NgModule({
  declarations: [AppComponent],
  imports: [
    StoreModule.forRoot(gsState),
    EffectsModule.forRoot(gsEffects),
    // RouterModule.forRoot(routes),
    BrowserModule,
    // RouterOutlet,
    LibraryModule,
    PlaybackModule,
  ],
  providers: [
    // provideRouter(routes),
    importProvidersFrom(
      HttpClientModule,
      StoreRootModule,
      EffectsRootModule,
      LibraryModule
    )
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
