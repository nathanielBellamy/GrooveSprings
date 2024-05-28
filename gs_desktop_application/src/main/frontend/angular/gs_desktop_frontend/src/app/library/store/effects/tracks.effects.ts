import {Injectable} from "@angular/core";
import { Actions, createEffect, ofType } from '@ngrx/effects';
import {map, exhaustMap, catchError, of} from "rxjs";
import {LibraryActionTypes} from '../library.actiontypes'
import {FetchTracksFailure, FetchTracksSuccess} from "../actions/tracks.actions";
import {TracksService} from "../../services/tracks.service";
import {TracksData} from "../../../models/tracks/tracks_data.model";

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

}
