import { Routes } from '@angular/router';
import {provideState, provideStore} from "@ngrx/store";
import {libraryReducer} from "./library/store/library.reducer";
import {LibraryComponent} from "./library/components/library/library.component";

export const routes: Routes = [
  {
    path: 'library',
    component: LibraryComponent,
    providers: [
      provideState({name: 'library', reducer: libraryReducer})
    ]
  }
];
