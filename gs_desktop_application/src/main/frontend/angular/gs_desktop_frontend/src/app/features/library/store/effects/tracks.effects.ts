import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, exhaustMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {
  FetchTracksFailure,
  FetchTracksSuccess,
  SetArtistsFilterTracksFailure,
  SetArtistsFilterTracksSuccess
} from "../actions/tracks.actions";
import {TracksService} from "../../services/tracks.service";
import {TracksData} from "../../../../models/tracks/tracks_data.model";
import {SetArtistsFilter} from "../actions/artists.actions";

@Injectable()
export class TracksEffects {
  constructor(
    private actions$: Actions,
    private tracksService: TracksService,
  ) {}

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

  fetchTracksByArtist$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetArtistsFilter>(LibraryActionTypes.SetArtistsFilter),
      map(action => action.payload),
      exhaustMap(artists => this.tracksService.fetchByArtistIds(artists.map(a => a.id))
        .pipe(
          map((payload) => new SetArtistsFilterTracksSuccess(payload)),
          catchError((e, _) => of(new SetArtistsFilterTracksFailure(e)))
        )
      )
    )
  )
}
