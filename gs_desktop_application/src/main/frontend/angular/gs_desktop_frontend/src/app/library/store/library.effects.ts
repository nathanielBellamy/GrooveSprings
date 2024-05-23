import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {ArtistService} from "../services/artists.service";
import {map, exhaustMap} from "rxjs";

@Injectable()
export class LibraryEffects {
  constructor(
    private actions$: Actions,
    private artistService: ArtistService
  ) {}

  fetchArtists$ = createEffect(() =>
    this.actions$.pipe(
      ofType('[Artists] Fetch Artists'),
      exhaustMap(() => this.artistService.fetchAll()
        .pipe(
          map((payload) => ({type: '[Artists] Fetch Artists Success', payload}))
        ))
    )
  )
}
