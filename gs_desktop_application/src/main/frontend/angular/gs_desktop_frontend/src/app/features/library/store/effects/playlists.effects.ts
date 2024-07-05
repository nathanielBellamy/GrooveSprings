import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, switchMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {PlaylistsService} from "../../services/playlists.service";
import {PlaylistsData} from "../../../../models/playlist/playlists_data.model";
import {
  FetchPlaylistsFailure,
  FetchPlaylistsSuccess,
  PlaylistCreateFailure,
  PlaylistCreateSuccess, PlaylistUpdateFailure, PlaylistUpdateSuccess
} from "../actions/playlists.actions";
import {Playlist} from "../../../../models/playlist/playlist.model";
import {FetchAll, PlaylistCreate, PlaylistUpdate, SetAlbumsFilter, SetArtistsFilter} from "../library.actions";

@Injectable()
export class PlaylistsEffects {
  constructor(
    private actions$: Actions,
    private playlistsService: PlaylistsService,
  ) {}

  fetchPlaylists$ = createEffect(() =>
    this.actions$.pipe(
      ofType<FetchAll | SetArtistsFilter | SetAlbumsFilter>(
        LibraryActionTypes.FetchAll,
        LibraryActionTypes.SetArtistsFilter,
        LibraryActionTypes.SetAlbumsFilter
      ),
      switchMap((action) => this.playlistsService.fetchByAction(action)
        .pipe(
          map((payload) =>new FetchPlaylistsSuccess(payload as PlaylistsData)),
          catchError((e, _) => of(new FetchPlaylistsFailure(e)))
        )
      )
    )
  )

  savePlaylist$ = createEffect(() =>
    this.actions$.pipe(
      ofType<PlaylistCreate>(LibraryActionTypes.PlaylistCreate),
      map(action => action.playlist),
      switchMap((playlist: Playlist) => this.playlistsService.create(playlist)
        .pipe(
          map((payload) => new PlaylistCreateSuccess()),
          catchError((e, _) => of(new PlaylistCreateFailure(e)))
        )
      )
    )
  )

  updatePlaylist$ = createEffect(() =>
    this.actions$.pipe(
      ofType<PlaylistUpdate>(LibraryActionTypes.PlaylistUpdate),
      map(action => action.dto),
      switchMap(dto => this.playlistsService.update(dto)
        .pipe(
          map((payload) => new PlaylistUpdateSuccess()),
          catchError((e, _) => of(new PlaylistUpdateFailure(e)))
        )
      )
    )
  )
}
