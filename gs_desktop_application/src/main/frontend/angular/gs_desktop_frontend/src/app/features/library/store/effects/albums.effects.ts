import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, switchMap, catchError, of} from "rxjs";
import {AlbumsService} from "../../services/albums.service";
import {LibraryActionTypes} from "../library.actiontypes";
import {
  FetchAlbumsFailure,
  FetchAlbumsSuccess,
  SetArtistsFilterAlbumsSuccess,
  SetPlaylistsFilterAlbumsSuccess
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
      ofType<FetchAll | SetArtistsFilter | SetPlaylistsFilter>(LibraryActionTypes.FetchAll),
      switchMap((action) => this.albumsService.fetchByAction(action)
        .pipe(
          map((payload) => new FetchAlbumsSuccess(payload as AlbumsData)),
          catchError((e, _) => of(new FetchAlbumsFailure(e)))
        )
      )
    )
  )

  // fetchAlbumsByArtist$ = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType<SetArtistsFilter>(LibraryActionTypes.SetArtistsFilter),
  //     map(action => action.payload),
  //     switchMap(artists => this.albumsService.fetchByArtistIds(artists.map(a => a.id))
  //       .pipe(
  //         map((payload) => new SetArtistsFilterAlbumsSuccess(payload)),
  //         catchError((e, _) => of(new FetchAlbumsFailure(e)))
  //       )
  //     )
  //   )
  // )
  //
  // fetchAlbumsByPlaylist$ = createEffect(() =>
  //   this.actions$.pipe(
  //     ofType<SetPlaylistsFilter>(LibraryActionTypes.SetPlaylistsFilter),
  //     map(action => action.payload),
  //     switchMap( playlists => this.albumsService.fetchByPlaylistIds(playlists.map(a => a.id))
  //       .pipe(
  //         map((payload) => new SetPlaylistsFilterAlbumsSuccess(payload)),
  //         catchError((e, _) => of(new FetchAlbumsFailure(e)))
  //       )
  //     )
  //   )
  // )
}
