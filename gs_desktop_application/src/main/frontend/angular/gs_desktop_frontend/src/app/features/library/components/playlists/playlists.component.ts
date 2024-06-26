import {Component, Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Playlist} from "../../../../models/playlist/playlist.model";
import {LibraryState} from "../../store/library.state";
import {Store} from "@ngrx/store";
import {SetPlaylistsFilter} from "../../store/library.actions";
import {SetPlaylistAsCurr} from "../../../playback/store/playback.actions";

@Component({
  selector: 'gsPlaylists',
  templateUrl: './playlists.component.html',
  styleUrl: './playlists.component.sass'
})
@Injectable()
export class PlaylistsComponent {
  protected playlists$: Observable<Playlist[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.playlists$ = store$.select(state => state.library.playlists)
  }

  handlePlaylistClick(playlist: Playlist) {
    this.store$.dispatch( new SetPlaylistsFilter([playlist]))
  }

  handleSetPlaylistAsCurrClick(playlist: Playlist) {
    this.store$.dispatch( new SetPlaylistAsCurr(playlist))
  }
}
