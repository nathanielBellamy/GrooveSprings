import {Component, Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Playlist} from "../../../../models/playlist/playlist.model";
import {LibraryState} from "../../store/library.state";
import {Store} from "@ngrx/store";
import {SetPlaylistsFilter} from "../../store/library.actions";

@Component({
  selector: 'gsPlaylists',
  templateUrl: './playlists.component.html',
  styleUrl: './playlists.component.sass'
})
@Injectable()
export class PlaylistsComponent {
  protected playlistCount$: Observable<number>
  protected playlists$: Observable<Playlist[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.playlistCount$ = store$.select(state => state.library.playlists.length)
    this.playlists$ = store$.select(state => state.library.playlists)
  }

  handlePlaylistClick(playlist: Playlist) {
    this.store$.dispatch( new SetPlaylistsFilter([playlist]))
  }
}
