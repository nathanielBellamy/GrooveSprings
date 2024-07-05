import { Component } from '@angular/core';
import {Track} from "../../../../models/tracks/track.model";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {ClearPlaylist, SetCurrPlaylistTrackIdx} from "../../store/playback.actions";
import {PlaylistCreate, PlaylistUpdate} from "../../../library/store/library.actions";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'gsPlaylist',
  templateUrl: './playlist.component.html',
  styleUrl: './playlist.component.sass'
})
export class PlaylistComponent {

  protected currIdx$: Observable<number>
  protected playlistId: number = 0
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
      this.setPlaylistName(state.playback.playlist.name) // harvest initial name
      return state.playback.currPlaylistTrackIdx
    })

    store$.subscribe(state => {
      this.playlistId = state.playback.playlist.id
      this.setPlaylistName(state.playback.playlist.name)
      this.playlistTracks = [...(state.playback.playlist.tracks || [])]
    })
  }

  handleDblClick(trackIdx: number, track: Track) {
    this.store$.dispatch(new SetCurrPlaylistTrackIdx(trackIdx))
  }

  handleSaveAsPlaylistClick(): void {
    this.store$.dispatch(new PlaylistCreate({
      id: 0,
      tracks: this.playlistTracks,
      name: this.getPlaylistName()
    }))
  }

  handleSavePlaylistClick(): void {
    if (this.playlistId > 0) { // update
      this.store$.dispatch(new PlaylistUpdate({
          id: this.playlistId,
          name: this.getPlaylistName(),
          trackIds: this.playlistTracks.map(t => t.id)
        })
      )
    } else { // create
      this.store$.dispatch(new PlaylistCreate({
        id: 0,
        tracks: this.playlistTracks,
        name: this.getPlaylistName()
      }))
    }
  }

  handleClearPlaylistClick(): void {
    this.playlistForm.get('name')?.setValue("")
    this.store$.dispatch(new ClearPlaylist())
  }

  setPlaylistName(name: string): void {
    this.playlistName = name
    this.playlistForm.get('name')?.setValue(this.playlistName)
  }

  getPlaylistName(): string {
    return this.playlistForm.get('name')?.getRawValue()
  }
}
