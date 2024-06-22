import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, switchMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {
  FetchTracksFailure,
  FetchTracksSuccess, SetAlbumsFilterTracksFailure, SetAlbumsFilterTracksSuccess,
  SetArtistsFilterTracksFailure,
  SetArtistsFilterTracksSuccess, SetPlaylistsFilterTracksFailure, SetPlaylistsFilterTracksSuccess
} from "../actions/tracks.actions";
import {TracksService} from "../../services/tracks.service";
import {TracksData} from "../../../../models/tracks/tracks_data.model";
import {SetAlbumsFilter, SetArtistsFilter, SetPlaylistsFilter} from "../library.actions";

@Injectable()
export class TracksEffects {
  constructor(
    private actions$: Actions,
    private tracksService: TracksService,
  ) {}

  fetchTracks$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchTracks, LibraryActionTypes.FetchAll),
      switchMap(() => this.tracksService.fetchAll()
        .pipe(
          map((payload) =>new FetchTracksSuccess(payload as TracksData)),
          catchError((e, _) => of(new FetchTracksFailure(e)))
        )
      )
    )
  )

  fetchTracksByArtistIds$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetArtistsFilter>(LibraryActionTypes.SetArtistsFilter),
      map(action => action.payload),
      switchMap(artists => this.tracksService.fetchByArtistIds(artists.map(a => a.id))
        .pipe(
          map((payload) => new SetArtistsFilterTracksSuccess(payload)),
          catchError((e, _) => of(new SetArtistsFilterTracksFailure(e)))
        )
      )
    )
  )

  fetchTracksByPlaylists$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetPlaylistsFilter>(LibraryActionTypes.SetPlaylistsFilter),
      map(action => action.payload),
      switchMap(playlists => this.tracksService.fetchByPlaylistIds(playlists.map(pl => pl.id))
        .pipe(
          map((payload) => new SetPlaylistsFilterTracksSuccess(payload)),
          catchError((e,_) => of(new SetPlaylistsFilterTracksFailure(e)))
        )
      )
    )
  )

  fetchTracksByAlbumIds$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetAlbumsFilter>(LibraryActionTypes.SetAlbumsFilter),
      map(action => action.payload),
      switchMap(albums => this.tracksService.fetchByAlbumIds(albums.map(a => a.id))
        .pipe(
          map((payload) => new SetAlbumsFilterTracksSuccess(payload)),
          catchError((e, _) => of(new SetAlbumsFilterTracksFailure(e)))
        )
      )
    )
  )
}
