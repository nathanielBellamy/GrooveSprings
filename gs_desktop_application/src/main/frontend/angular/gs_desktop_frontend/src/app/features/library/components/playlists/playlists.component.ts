import {Component, Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Playlist} from "../../../../models/playlist/playlist.model";
import {LibraryState} from "../../store/library.state";
import {Store} from "@ngrx/store";
import {SetPlaylistsFilter} from "../../store/library.actions";
import {SetPlaylistAsCurr} from "../../../playback/store/playback.actions";
import {PlaylistRepr} from "../../../../models/playlist/playlist_repr.model";

@Component({
  selector: 'gsPlaylists',
  templateUrl: './playlists.component.html',
  styleUrl: './playlists.component.sass'
})
@Injectable()
export class PlaylistsComponent {
  protected playlists$: Observable<PlaylistRepr[]>
  protected playlistsFilter$: Observable<PlaylistRepr[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.playlists$ = store$.select(state => state.library.playlists)
    this.playlistsFilter$ = store$.select(state => state.library.filters.playlists)
  }

  handlePlaylistClick(playlist: PlaylistRepr) {
    this.store$.dispatch( new SetPlaylistsFilter([playlist]))
  }

  handleSetPlaylistAsCurrClick(playlist: PlaylistRepr) {
    this.store$.dispatch( new SetPlaylistAsCurr(playlist))
  }
}
