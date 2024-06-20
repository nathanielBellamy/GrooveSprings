import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, switchMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {PlaylistsService} from "../../services/playlists.service";
import {PlaylistsData} from "../../../../models/playlist/playlists_data.model";
import {FetchPlaylistsFailure, FetchPlaylistsSuccess} from "../actions/playlists.actions";

@Injectable()
export class PlaylistsEffects {
  constructor(
    private actions$: Actions,
    private playlistsService: PlaylistsService,
  ) {}

  fetchAllPlaylists$ = createEffect(() =>
    this.actions$.pipe(
      ofType(LibraryActionTypes.FetchPlaylists, LibraryActionTypes.FetchAll),
      switchMap(() => this.playlistsService.fetchAll()
        .pipe(
          map((payload) =>new FetchPlaylistsSuccess(payload as PlaylistsData)),
          catchError((e, _) => of(new FetchPlaylistsFailure(e)))
        )
      )
    )
  )
}
