import { StoreModule } from '@ngrx/store'
import { libraryReducer } from './library.reducer'
import { NgModule } from '@angular/core'
import {EffectsModule} from "@ngrx/effects";
import {LibraryEffects} from "./library.effects";

@NgModule({
  imports: [
    StoreModule.forRoot({libraryStore: libraryReducer}),
    EffectsModule.forRoot(LibraryEffects),
  ],
  providers: []
})
export class LibraryModule {}
