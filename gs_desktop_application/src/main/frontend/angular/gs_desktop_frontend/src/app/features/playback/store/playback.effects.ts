import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {catchError, map, switchMap, of} from "rxjs";
import {
  ClearPlaylist,
  SetCurrFileFailure,
  SetCurrFileSuccess,
  SetCurrPlaylistTrackIdx,
  SetCurrTrack
} from "./playback.actions";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {PlaybackService} from "../services/playback.service";

@Injectable()
export class PlaybackEffects {

  constructor(
    private actions$: Actions,
    private playbackService: PlaybackService
  ) { }

  setCurrFile$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrTrack>(PlaybackActionTypes.SetCurrTrack),
      map(action => action.getTrack()),
      switchMap(track => this.playbackService.setCurrFile(track)
        .pipe(
          map(() => new SetCurrFileSuccess()),
          catchError((e, _) => of(new SetCurrFileFailure(e)))
        )
      )
    )
  )

  setCurrFileOnPlaylistIndexUpdate$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrPlaylistTrackIdx>(PlaybackActionTypes.SetCurrPlaylistTrackIdx),
      map(action => new SetCurrTrack(action.getTrack()))
    )
  )

  clearPlaylistOnLibraryClear$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    map(() => new ClearPlaylist())
  ))
}
