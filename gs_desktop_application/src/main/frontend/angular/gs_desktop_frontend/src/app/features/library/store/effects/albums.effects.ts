import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, exhaustMap, catchError, of} from "rxjs";
import {AlbumsService} from "../../services/albums.service";
import {LibraryActionTypes} from "../library.actiontypes";
import {FetchAlbumsFailure, FetchAlbumsSuccess, SetArtistsFilterAlbumsSuccess} from "../actions/albums.actions";
import {AlbumsData} from "../../../../models/albums/albums_data.model";
import {SetArtistsFilter} from "../actions/artists.actions";
import {AlbumsGetByArtistIds} from "../../../../models/albums/albums_get_by_artist_ids.model";

@Injectable()
export class AlbumsEffects {
  constructor(
    private actions$: Actions,
    private albumsService: AlbumsService,
  ) { }

  fetchAlbums$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchAlbums),
      exhaustMap(() => this.albumsService.fetchAll()
        .pipe(
          map((payload) => {
            return new FetchAlbumsSuccess(payload as AlbumsData)
          }),
          catchError((e, _) => of(new FetchAlbumsFailure(e)))
        )
      )
    )
  )

  fetchAlbumsByArtist$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetArtistsFilter>(LibraryActionTypes.SetArtistsFilter),
      map(action => action.payload),
      exhaustMap(artists => this.albumsService.fetchByArtistIds(artists.map(a => a.id))
        .pipe(
          map((payload) => {
            return new SetArtistsFilterAlbumsSuccess(payload)
          }),
          catchError((e, _) => of(new FetchAlbumsFailure(e)))
        )
      )
    )
  )

}
