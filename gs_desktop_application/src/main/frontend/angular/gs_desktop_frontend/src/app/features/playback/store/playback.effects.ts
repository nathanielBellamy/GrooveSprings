import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {catchError, map, switchMap, of} from "rxjs";
import {
  ClearPlaylist, FetchLastState, FetchLastStateFailure, NextTrack, PauseTrig, PlaybackSpeedTrig, PlayTrig,
  SetCurrFileFailure,
  SetCurrFileSuccess,
  SetCurrPlaylistTrackIdx,
  SetCurrTrack, SetPlaybackState, SetPlaylistAsCurr, SetPlaylistAsCurrFailure, SetPlaylistAsCurrSuccess, StopTrig
} from "./playback.actions";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {PlaybackService} from "../services/playback.service";
import {Track} from "../../../models/tracks/track.model";
import {Identity} from "../../library/store/library.actions";
import {PlaybackState} from "./playback.state";
import {Store} from "@ngrx/store";

@Injectable()
export class PlaybackEffects {
  private state: PlaybackState | Object = {}

  constructor(
    private actions$: Actions,
    private playbackService: PlaybackService,
    private store$: Store<{playback: PlaybackState}>
  ) {
    store$.subscribe((state) => this.state = {...state.playback})
  }

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

  fetchLastState$ = createEffect(() =>
    this.actions$.pipe(
      ofType(PlaybackActionTypes.FetchLastState),
      switchMap(() => this.playbackService.fetchLastState()
        .pipe(
          map((res: any) => {
            let parsedState: PlaybackState = JSON.parse(res)
            // check for valid parse
            if (!parsedState.hasOwnProperty('currTrack')) return new FetchLastStateFailure()
            return new SetPlaybackState(parsedState)
          }),
          catchError((e,_) => of(new FetchLastStateFailure))
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

  cachePlaybackState$ = createEffect(() =>
    this.actions$.pipe(
      ofType(
        PlaybackActionTypes.AddTrackToPlaylist,
        PlaybackActionTypes.ClearPlaylist,
        PlaybackActionTypes.SetPlaylistAsCurrSuccess,
        PlaybackActionTypes.SetCurrPlaylistTrackIdx,
      ),
      switchMap(() => this.playbackService.cacheState()
        .pipe(
          map(() => new Identity()),
          catchError((e, _) => of(new Identity()))
        )
      )
    )
  )

  playNextTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType<NextTrack>(
        PlaybackActionTypes.NextTrack
      ),
      map(() => {
        const state = this.state as PlaybackState
        return new SetCurrTrack(state.playlist.tracks[state.currPlaylistTrackIdx], false)
      })
    )
  )
}
