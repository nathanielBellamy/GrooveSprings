import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {ArtistsService} from "../services/artists.service";
import {map, exhaustMap, catchError, of} from "rxjs";
import {
  FetchArtistsSuccess,
  FetchArtistsFailure,
  FetchAlbumsSuccess,
  FetchTracksSuccess,
  FetchTracksFailure
} from "./library.actions";
import {LibraryActionTypes} from './library.actiontypes'
import {AlbumsService} from "../services/albums.service";
import {AlbumsData} from "../../models/albums/albums_data.model";
import {TracksData} from "../../models/tracks/tracks_data.model";
import {TracksService} from "../services/tracks.service";

@Injectable()
export class LibraryEffects {
  constructor(
    private actions$: Actions,
    private albumsService: AlbumsService,
    private artistService: ArtistsService,
    private tracksService: TracksService,
  ) {}

  fetchArtists$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchArtists),
      exhaustMap(() => this.artistService.fetchAll()
        .pipe(
          map((payload) => {
            return new FetchArtistsSuccess(payload)
          }),
          catchError((e, _) => of(new FetchArtistsFailure(e)))
        )
      )
    )
  )

  fetchAlbums$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchAlbums),
      exhaustMap(() => this.albumsService.fetchAll()
        .pipe(
          map((payload) => {
            return new FetchAlbumsSuccess(payload as AlbumsData)
          }),
          catchError((e, _) => of(new FetchArtistsFailure(e)))
        )
      )
    )
  )

  fetchTracks$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchTracks),
      exhaustMap(() => this.tracksService.fetchAll()
        .pipe(
          map((payload) => {
            return new FetchTracksSuccess(payload as TracksData)
          }),
          catchError((e, _) => of(new FetchTracksFailure(e)))
        )
      )
    )
  )

}
