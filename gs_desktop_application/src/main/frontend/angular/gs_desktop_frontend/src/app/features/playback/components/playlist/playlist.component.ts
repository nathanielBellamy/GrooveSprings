import { Component } from '@angular/core';
import {Track} from "../../../../models/tracks/track.model";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {ClearPlaylist, SetCurrPlaylistTrackIdx, UpdateCurrPlaylistTrackidx} from "../../store/playback.actions";
import {defaultPlaylist, Playlist} from "../../../../models/playlist/playlist.model";
import {PlaylistCreate} from "../../../library/store/library.actions";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'gsPlaylist',
  templateUrl: './playlist.component.html',
  styleUrl: './playlist.component.sass'
})
export class PlaylistComponent {

  protected currIdx$: Observable<number>
  protected playlistName: string = ""
  protected playlistTracks: Track[] = []

  playlistForm = new FormGroup({
    name: new FormControl<string>(this.playlistName, [
      Validators.minLength(3),
      Validators.required
    ])
  })

  constructor(private store$: Store<{playback: PlaybackState}>) {

    this.currIdx$ = store$.select(state => {
      this.playlistName = state.playback.playlist.name // harvest the initial playlistName
      return state.playback.currPlaylistTrackIdx
    })

    store$.subscribe(state => {
      this.playlistTracks = [...state.playback.playlist.tracks]
    })
  }

  handleDblClick(trackIdx: number, track: Track) {
    this.store$.dispatch(new SetCurrPlaylistTrackIdx(trackIdx, track))
  }

  handleSavePlaylistClick() {
    this.store$.dispatch(new PlaylistCreate({
      id: 0,
      tracks: this.playlistTracks,
      name: this.playlistName
    }))
  }

  handleClearPlaylistClick() {
    this.store$.dispatch(new ClearPlaylist())
  }

  updateCurrPlaylistTrackIdx(byVal: number) {
    this.store$.dispatch(new UpdateCurrPlaylistTrackidx(byVal))
  }
}
