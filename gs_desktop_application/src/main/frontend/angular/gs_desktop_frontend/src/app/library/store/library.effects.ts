import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {ArtistService} from "../services/artists.service";
import {map, exhaustMap, catchError, of} from "rxjs";
import { FetchArtistsSuccess, FetchArtistsFailure } from "./library.actions";
import {Store} from "@ngrx/store";

@Injectable()
export class LibraryEffects {
  constructor(
    private actions$: Actions,
    private artistService: ArtistService,
    private store$: Store
  ) {}

  fetchArtists$ = createEffect(() =>
    this.actions$.pipe(
      ofType('[Artists] Fetch Artists'),
      exhaustMap(() => this.artistService.fetchAll()
        .pipe(
          map((payload) => new FetchArtistsSuccess(payload)),
          catchError(((e, _) => {
            console.error(e)
            return  of(new FetchArtistsFailure(e))
          })
        ))
     )
    )
  )
}
