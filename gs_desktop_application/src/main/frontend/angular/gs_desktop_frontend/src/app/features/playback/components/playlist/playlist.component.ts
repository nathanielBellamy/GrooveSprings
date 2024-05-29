import { Component } from '@angular/core';
import {Track} from "../../../../models/tracks/track.model";
import {Observable} from "rxjs";
import {Store} from "@ngrx/store";
import {PlaybackState} from "../../store/playback.state";
import {SetCurrPlaylistTrackIdx} from "../../store/playback.actions";

@Component({
  selector: 'gsPlaylist',
  templateUrl: './playlist.component.html',
  styleUrl: './playlist.component.sass'
})
export class PlaylistComponent {

  protected currIdx$: Observable<number>
  protected playlistName$: Observable<string>
  protected playlistTracks$: Observable<Track[]>

  constructor(private store$: Store<{playback: PlaybackState}>) {

    this.currIdx$ = store$.select(state => state.playback.currPlaylistTrackIdx)
    this.playlistName$ = store$.select(state => state.playback.playlist.name)
    this.playlistTracks$ = store$.select(state => state.playback.playlist.tracks)
  }

  handleDblClick(trackIdx: number) {
    this.store$.dispatch(new SetCurrPlaylistTrackIdx(trackIdx))
  }
}
