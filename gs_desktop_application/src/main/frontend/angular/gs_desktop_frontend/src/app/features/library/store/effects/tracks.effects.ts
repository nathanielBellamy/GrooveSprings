import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, switchMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {
  FetchTracksFailure,
  FetchTracksSuccess
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
      ofType<SetArtistsFilter | SetAlbumsFilter | SetPlaylistsFilter>(
        LibraryActionTypes.SetArtistsFilter,
        LibraryActionTypes.SetAlbumsFilter,
        LibraryActionTypes.SetPlaylistsFilter
      ),
      switchMap((action) => this.tracksService.fetchByAction(action)
        .pipe(
          // TODO: return correct action by type?
          map((payload) => new FetchTracksSuccess(payload as TracksData)),
          catchError((e, _) => of(new FetchTracksFailure(e)))
        )
      )
    )
  )
}
