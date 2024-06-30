import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {ArtistsService} from "../../services/artists.service";
import {map, switchMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {
  FetchArtistsFailure,
  FetchArtistsSuccess,
} from "../actions/artists.actions";
import {FetchAll, SetAlbumsFilter, SetPlaylistsFilter} from "../library.actions";
import {ArtistsData} from "../../../../models/artists/artists_data.model";

@Injectable()
export class ArtistsEffects {
  constructor(
    private actions$: Actions,
    private artistService: ArtistsService,
  ) {}

  fetchArtists$ = createEffect(() =>
    this.actions$.pipe(
      ofType<FetchAll | SetAlbumsFilter | SetPlaylistsFilter>(
        LibraryActionTypes.FetchAll,
        LibraryActionTypes.SetAlbumsFilter,
        LibraryActionTypes.SetPlaylistsFilter
      ),
      switchMap((action) => this.artistService.fetchByAction(action)
        .pipe(
          map((payload) => new FetchArtistsSuccess(payload as ArtistsData)),
          catchError((e, _) => of(new FetchArtistsFailure(e)))
        )
      )
    )
  )
}
