import { Component, OnInit } from '@angular/core';
import {Store} from "@ngrx/store";
import {LibraryState} from "../../store/library.state";
import {Observable} from "rxjs";
import {FetchTracks} from "../../store/library.actions";
import {Track} from "../../../../models/tracks/track.model";
import {AddTrackToPlaylist} from "../../../playback/store/playback.actions";

@Component({
  selector: 'gsTracks',
  templateUrl: './tracks.component.html',
  styleUrl: './tracks.component.sass'
})
export class TracksComponent implements OnInit {
  public trackCount$: Observable<number>
  public tracks$: Observable<Track[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.trackCount$ = store$.select(state => state.library.trackCount)
    this.tracks$ = store$.select(state => state.library.tracks)
  }

  ngOnInit() {
    this.store$.dispatch(new FetchTracks())
  }

  handleDblClick(track: Track) {
    this.store$.dispatch(new AddTrackToPlaylist(track))
  }
}
