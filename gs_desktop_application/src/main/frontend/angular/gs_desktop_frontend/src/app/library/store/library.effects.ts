import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {ArtistsService} from "../services/artists.service";
import {map, exhaustMap, catchError, of} from "rxjs";
import {FetchArtistsSuccess, FetchArtistsFailure, FetchAlbumsSuccess} from "./library.actions";
import {Store} from "@ngrx/store"
import {LibraryActionTypes} from './library.actiontypes'
import {AlbumsService} from "../services/albums.service";
import {AlbumsGetAll} from "../../models/albums/albums_get_all.model";
import {AlbumsData} from "../../models/albums/albums_data.model";

@Injectable()
export class LibraryEffects {
  constructor(
    private actions$: Actions,
    private albumsService: AlbumsService,
    private artistService: ArtistsService,
    private store$: Store
  ) {}

  fetchArtists$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchArtists),
      exhaustMap(() => this.artistService.fetchAll()
        .pipe(
          map((payload) => {
            return new FetchArtistsSuccess(payload)
          }),
          catchError(((e, _) => {
            console.log("fetchArtists error")
            console.error(e)
            return  of(new FetchArtistsFailure(e))
          })
        ))
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
          catchError(((e, _) => {
            console.log("fetchAlbums error")
            console.error(e)
            return of(new FetchArtistsFailure(e))
          })
        ))
      )
    )
  )


}
