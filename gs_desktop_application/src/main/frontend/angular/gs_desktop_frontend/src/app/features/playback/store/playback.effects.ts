import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {catchError, map, switchMap, of} from "rxjs";
import {
  AddTrackToPlaylist,
  ClearPlaylist, FetchAppState, NextTrackTrig, PauseTrig, PlaybackSpeedTrig, PlayTrig, PrevTrackTrig,
  SetCurrFileFailure,
  SetCurrFileSuccess,
  SetCurrPlaylistTrackIdx,
  SetCurrTrack, SetPlaylistAsCurr, SetPlaylistAsCurrFailure, StopTrig
} from "./playback.actions";
import {PlaybackActionTypes} from "./playback.actiontypes";
import {PlaybackService} from "../services/playback.service";
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

  fetchAppState$ = createEffect(() =>
    this.actions$.pipe(
      ofType<FetchAppState>(PlaybackActionTypes.FetchAppState),
      switchMap(() => this.playbackService.fetchAppState()
        .pipe(
          map(() => new Identity())
        )
      )
    )
  )

  setPlaylistAsCurr$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetPlaylistAsCurr>(PlaybackActionTypes.SetPlaylistAsCurr),
      switchMap((action) =>
        this.playbackService.fetchPlaylistTracks(action.payload)
          .pipe(
            switchMap((res => {
              const playlist = {
                id: res.data.playlist.id,
                name: res.data.playlist.name,
                tracks: [...res.data.tracks]
              }
              return this.playbackService.setPlaylist(playlist)
            })),
            map(() => new Identity()),
            catchError((e, _) => of(new SetPlaylistAsCurrFailure()))
          )
      )
    )
  )

  setCurrTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrTrack>(PlaybackActionTypes.SetCurrTrack),
      switchMap((action) => this.playbackService.setCurrTrack(action.getTrack())
        .pipe(
          map(() => new SetCurrFileSuccess(action.initialLoad)),
          catchError((e, _) => of(new SetCurrFileFailure(e)))
        )
      )
    )
  )

  setCurrPlaylistTrackIdx$ = createEffect(() =>
    this.actions$.pipe(
      ofType<SetCurrPlaylistTrackIdx>(PlaybackActionTypes.SetCurrPlaylistTrackIdx),
      switchMap((action) => this.playbackService.setCurrPlaylistTrackIdx(action.trackIdx)
        .pipe(
          map(() => new Identity())
        )
      )
    )
  )

  clearPlaylistOnLibraryClear$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    map(() => new ClearPlaylist())
  ))

  transportControlTrig$ = createEffect(() =>
    this.actions$.pipe(
      ofType<PlayTrig | PauseTrig | StopTrig | PlaybackSpeedTrig | NextTrackTrig | PrevTrackTrig>(
        PlaybackActionTypes.PlayTrig,
        PlaybackActionTypes.PauseTrig,
        PlaybackActionTypes.StopTrig,
        PlaybackActionTypes.PlaybackSpeedTrig,
        PlaybackActionTypes.NextTrackTrig,
        PlaybackActionTypes.PrevTrackTrig
      ),
      switchMap((action) => this.playbackService.transportControlTrig(action)
        .pipe(
          map(() => new Identity()),
          catchError((e, _) => of(new Identity()))
        )
      )
    )
  )

  addTrackToPlaylist$ = createEffect(() =>
    this.actions$.pipe(
      ofType<AddTrackToPlaylist>(
        PlaybackActionTypes.AddTrackToPlaylist
      ),
      map(action => action.payload),
      switchMap(track => this.playbackService.addTrackToPlaylist(track)
        .pipe(
          map(() => new Identity()),
          catchError((e, _) => of(new Identity()))
        )
      )
    )
  )

  clearPlaylist$ = createEffect(() =>
    this.actions$.pipe(
      ofType<ClearPlaylist>(
        PlaybackActionTypes.ClearPlaylist
      ),
      switchMap(track => this.playbackService.clearPlaylist()
        .pipe(
          map(() => new Identity()),
          catchError((e, _) => of(new Identity()))
        )
      )
    )
  )
}
