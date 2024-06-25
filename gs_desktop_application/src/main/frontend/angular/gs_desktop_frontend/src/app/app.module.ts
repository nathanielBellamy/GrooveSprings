// app.module.ts
//
import {EffectsModule, EffectsRootModule, provideEffects} from "@ngrx/effects";
import {importProvidersFrom, NgModule} from "@angular/core";
import {StoreModule, StoreRootModule} from "@ngrx/store";
// import {provideRouter, RouterModule, RouterOutlet} from "@angular/router";
import {AppComponent} from "./app.component";
import {PlaybackModule} from "./features/playback/playback.module";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {gsState} from "./store/app.state";
import {gsEffects} from "./store/app.effects";
import {LibraryModule} from "./features/library/components/library/library.module";
import {ContainerModule} from "./features/container/components/container/container.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    StoreModule.forRoot(gsState),
    EffectsModule.forRoot(gsEffects),
    // RouterModule.forRoot(routes),
    BrowserModule,
    // RouterOutlet,
    LibraryModule,
    PlaybackModule,
    ContainerModule
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
