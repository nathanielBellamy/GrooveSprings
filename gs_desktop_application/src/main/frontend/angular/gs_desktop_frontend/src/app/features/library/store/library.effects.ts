import {AlbumsEffects} from "./effects/albums.effects";
import {ArtistsEffects} from "./effects/artists.effects";
import {TracksEffects} from "./effects/tracks.effects";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {Injectable} from "@angular/core";
import {LibraryActionTypes} from "./library.actiontypes";
import {FetchTracks} from "./actions/tracks.actions";
import {catchError, exhaustMap, map, of} from "rxjs";
import {
  FetchAlbumsFailure,
  FetchAlbumsSuccess,
  FetchAll,
  LibraryScanSuccess,
  LibraryScanFailure, ClearLibraryFailure, ClearLibrarySuccess
} from "./library.actions";
import {LibraryService} from "../services/library.service";
import {AlbumsData} from "../../../models/albums/albums_data.model";

@Injectable()
export class LibraryEffects {

  constructor(
    private actions$: Actions,
    private libraryService: LibraryService
  ){ }

  fetchAll$ = createEffect(() => this.actions$.pipe(
    ofType(
      LibraryActionTypes.ClearArtistsFilter,
      LibraryActionTypes.ClearAlbumsFilter,
      LibraryActionTypes.LibraryScanSuccess,
      LibraryActionTypes.ClearLibrarySuccess
    ),
    map(() => new FetchAll())
  ))

  runScan$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.LibraryScan),
    exhaustMap(() => this.libraryService.runScan()
      .pipe(
        map(() => new LibraryScanSuccess()),
        catchError((e, _) => of(new LibraryScanFailure(e)))
      )
    )
  ))

  clearLib$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    exhaustMap(() => this.libraryService.clearLib()
      .pipe(
        map(() => new ClearLibrarySuccess()),
        catchError((e, _) => of(new ClearLibraryFailure(e)))
      )
    )
  ))

}

export const libraryEffects: any[] = [LibraryEffects, AlbumsEffects, ArtistsEffects, TracksEffects]
