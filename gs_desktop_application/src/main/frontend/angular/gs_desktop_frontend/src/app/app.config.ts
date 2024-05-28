import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideStore, StoreModule, StoreRootModule} from '@ngrx/store';
import {EffectsRootModule, provideEffects} from '@ngrx/effects';
import {libraryReducer} from "./library/store/library.reducer";
import {libraryEffects} from "./library/store/library.effects";
import {ArtistsService} from "./library/services/artists.service";
import {LibraryModule} from "./library/components/library/library.module";

export const appConfig: ApplicationConfig = {
  providers: [
    ArtistsService,
    provideRouter(routes),
    importProvidersFrom(HttpClientModule, StoreRootModule, EffectsRootModule, LibraryModule),
    provideStore({
      library: libraryReducer
    }),
    provideEffects([...libraryEffects]),
  ]
};
