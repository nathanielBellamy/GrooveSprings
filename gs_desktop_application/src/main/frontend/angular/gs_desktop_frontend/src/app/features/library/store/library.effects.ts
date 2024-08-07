import {AlbumsEffects} from "./effects/albums.effects";
import {ArtistsEffects} from "./effects/artists.effects";
import {TracksEffects} from "./effects/tracks.effects";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {Injectable} from "@angular/core";
import {LibraryActionTypes} from "./library.actiontypes";
import {catchError, switchMap, map, of} from "rxjs";
import {
  FetchAll,
  LibraryScanSuccess,
  LibraryScanFailure, ClearLibraryFailure, ClearLibrarySuccess
} from "./library.actions";
import {LibraryService} from "../services/library.service";
import {PlaylistsEffects} from "./effects/playlists.effects";

@Injectable()
export class LibraryEffects {

  constructor(
    private actions$: Actions,
    private libraryService: LibraryService
  ){ }

  fetchAll$ = createEffect(() => this.actions$.pipe(
    ofType(
      LibraryActionTypes.ClearFilters,
      LibraryActionTypes.LibraryScanSuccess,
      LibraryActionTypes.ClearLibrarySuccess
    ),
    map(() => new FetchAll())
  ))

  runScan$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.LibraryScan),
    switchMap(() => this.libraryService.runScan()
      .pipe(
        map(() => new LibraryScanSuccess()),
        catchError((e, _) => of(new LibraryScanFailure(e)))
      )
    )
  ))

  clearLib$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    switchMap(() => this.libraryService.clearLib()
      .pipe(
        map(() => new ClearLibrarySuccess()),
        catchError((e, _) => of(new ClearLibraryFailure(e)))
      )
    )
  ))

}

export const libraryEffects: any[] = [
  LibraryEffects,
  AlbumsEffects,
  ArtistsEffects,
  PlaylistsEffects,
  TracksEffects
]
