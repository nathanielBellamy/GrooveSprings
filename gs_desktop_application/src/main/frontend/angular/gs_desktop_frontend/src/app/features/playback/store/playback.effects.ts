import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {catchError, map, switchMap, of} from "rxjs";
import {ClearPlaylist, SetCurrFileFailure, SetCurrFileSuccess, SetCurrPlaylistTrackIdx} from "./playback.actions";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {PlaybackService} from "../services/playback.service";
import {SetAlbumsFilterTracksFailure, SetAlbumsFilterTracksSuccess} from "../../library/store/actions/tracks.actions";

@Injectable()
export class PlaybackEffects {

  constructor(
    private actions$: Actions,
    private playbackService: PlaybackService
  ) { }

  // TODO: set curr file path --> play track
  setCurrFile$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrPlaylistTrackIdx>(PlaybackActionTypes.SetCurrPlaylistTrackIdx),
      map(action => action.getTrack()),
      switchMap(track => this.playbackService.setCurrFile(track)
        .pipe(
          map(() => new SetCurrFileSuccess()),
          catchError((e, _) => of(new SetCurrFileFailure(e)))
        )
      )
    )
  )

  clearPlaylistOnLibraryClear$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    map(() => new ClearPlaylist())
  ))
}
