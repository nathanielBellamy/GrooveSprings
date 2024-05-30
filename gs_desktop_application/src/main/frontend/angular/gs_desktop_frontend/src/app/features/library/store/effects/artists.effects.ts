import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {ArtistsService} from "../../services/artists.service";
import {map, exhaustMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {FetchArtistsFailure, FetchArtistsSuccess} from "../actions/artists.actions";

@Injectable()
export class ArtistsEffects {
  constructor(
    private actions$: Actions,
    private artistService: ArtistsService,
  ) {}

  fetchArtists$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchArtists, LibraryActionTypes.FetchAll),
      exhaustMap(() => this.artistService.fetchAll()
        .pipe(
          map((payload) => new FetchArtistsSuccess(payload)),
          catchError((e, _) => of(new FetchArtistsFailure(e)))
        )
      )
    )
  )
}
