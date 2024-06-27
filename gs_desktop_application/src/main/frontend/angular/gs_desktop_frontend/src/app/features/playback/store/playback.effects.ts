import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {catchError, map, switchMap, of} from "rxjs";
import {
  ClearPlaylist, FetchLastTrackFailure, PauseTrig, PlaybackSpeedTrig, PlayTrig,
  SetCurrFileFailure,
  SetCurrFileSuccess,
  SetCurrPlaylistTrackIdx,
  SetCurrTrack, SetPlaylistAsCurr, SetPlaylistAsCurrFailure, SetPlaylistAsCurrSuccess, StopTrig
} from "./playback.actions";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {PlaybackService} from "../services/playback.service";
import {Track} from "../../../models/tracks/track.model";
import {Identity} from "../../library/store/library.actions";

@Injectable()
export class PlaybackEffects {

  constructor(
    private actions$: Actions,
    private playbackService: PlaybackService
  ) { }

  getPlaylistTracks$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetPlaylistAsCurr>(PlaybackActionTypes.SetPlaylistAsCurr),
      switchMap((action) => this.playbackService.fetchPlaylistTracks(action.payload)
        .pipe(
          map((res) => new SetPlaylistAsCurrSuccess({
            id: res.data.playlist.id,
            name: res.data.playlist.name,
            tracks: res.data.tracks
          })),
          catchError((e, _) => of(new SetPlaylistAsCurrFailure()))
        )
      )
    )
  )

  fetchLastTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PlaybackActionTypes.FetchLastTrack),
      switchMap(() => this.playbackService.fetchLastTrack()
        .pipe(
          map((res: any) => {
            let parsedTrack: Track = JSON.parse(res)
            // check for valid parse
            if (!parsedTrack.hasOwnProperty('path')) return new FetchLastTrackFailure()
            return new SetCurrTrack(parsedTrack, true)
          }),
          catchError((e,_) => of(new FetchLastTrackFailure))
        )
      )
    )
  )

  setCurrTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrTrack>(PlaybackActionTypes.SetCurrTrack),
      switchMap((action) => this.playbackService.setCurrFile(action.getTrack())
        .pipe(
          map(() => new SetCurrFileSuccess(action.initialLoad)),
          catchError((e, _) => of(new SetCurrFileFailure(e)))
        )
      )
    )
  )

  playOnSetCurrTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrFileSuccess>(PlaybackActionTypes.SetCurrFileSuccess),
      map((action) => {
        if (action.initialLoad) return new Identity()
        return new PlayTrig()
      })
    )
  )

  setCurrFileOnPlaylistIndexUpdate$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrPlaylistTrackIdx>(PlaybackActionTypes.SetCurrPlaylistTrackIdx),
      map(action => new SetCurrTrack(action.getTrack(), false))
    )
  )

  clearPlaylistOnLibraryClear$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    map(() => new ClearPlaylist())
  ))

  transportControlTrig$ = createEffect(() =>
    this.actions$.pipe(
      ofType<PlayTrig | PauseTrig | StopTrig | PlaybackSpeedTrig>(
        PlaybackActionTypes.PlayTrig,
        PlaybackActionTypes.PauseTrig,
        PlaybackActionTypes.StopTrig,
        PlaybackActionTypes.PlaybackSpeedTrig
      ),
      switchMap((action) => this.playbackService.transportControlTrig(action)
        .pipe(
          map(() => new Identity()),
          catchError((e, _) => of(new Identity()))
        )
      )
    )
  )
}
