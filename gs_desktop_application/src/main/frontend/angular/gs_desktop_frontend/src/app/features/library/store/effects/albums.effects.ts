import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, switchMap, catchError, of} from "rxjs";
import {AlbumsService} from "../../services/albums.service";
import {LibraryActionTypes} from "../library.actiontypes";
import {
  FetchAlbumsFailure,
  FetchAlbumsSuccess,
} from "../actions/albums.actions";
import {AlbumsData} from "../../../../models/albums/albums_data.model";

import {FetchAll, SetArtistsFilter, SetPlaylistsFilter} from "../library.actions";

@Injectable()
export class AlbumsEffects {
  constructor(
    private actions$: Actions,
    private albumsService: AlbumsService,
  ) { }

  fetchAlbums$ = createEffect(() =>
    this.actions$.pipe(
      ofType<FetchAll | SetArtistsFilter | SetPlaylistsFilter>(
        LibraryActionTypes.FetchAll,
        LibraryActionTypes.SetArtistsFilter,
        LibraryActionTypes.SetPlaylistsFilter
      ),
      switchMap((action) => this.albumsService.fetchByAction(action)
        .pipe(
          map((payload) => new FetchAlbumsSuccess(payload as AlbumsData)),
          catchError((e, _) => of(new FetchAlbumsFailure(e)))
        )
      )
    )
  )
}
