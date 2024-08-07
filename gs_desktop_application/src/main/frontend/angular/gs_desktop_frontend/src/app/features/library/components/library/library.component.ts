import {Component, OnInit} from '@angular/core';
import { Injectable } from '@angular/core';
import {LibraryState} from "../../store/library.state";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Album} from "../../../../models/albums/album.model";
import {Artist} from "../../../../models/artists/artist.model";
import {FetchArtists} from "../../store/actions/artists.actions";
import {FetchAlbums} from "../../store/actions/albums.actions";
import {ClearFilters, ClearLibrary, FetchAll, LibraryScan} from "../../store/library.actions";
import {FetchPlaylists} from "../../store/actions/playlists.actions";
import {Playlist} from "../../../../models/playlist/playlist.model";
import {PlaylistRepr} from "../../../../models/playlist/playlist_repr.model";

@Component({
  selector: 'gsLibrary',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
@Injectable()
export class LibraryComponent implements OnInit {

  protected artistsFilter$: Observable<Artist[]>
  protected albumsFilter$: Observable<Album[]>
  protected playlistsFilter$: Observable<PlaylistRepr[]>
  protected playlistCount$: Observable<number>
  protected artistCount$: Observable<number>
  protected albumCount$: Observable<number>
  protected trackCount$: Observable<number>

  protected currSection: string = 'library'

  constructor(private store$: Store<{library: LibraryState}>) {
    this.artistsFilter$ = store$.select(state => state.library.filters.artists)
    this.albumsFilter$ = store$.select(state =>  state.library.filters.albums)
    this.playlistsFilter$ = store$.select(state => state.library.filters.playlists)
    this.playlistCount$ = store$.select(state => state.library.playlistCount)
    this.artistCount$ = store$.select(state => state.library.artistCount)
    this.albumCount$ = store$.select(state => state.library.albumCount)
    this.trackCount$ = store$.select(state => state.library.trackCount)
  }

  ngOnInit() {
    this.store$.dispatch(new FetchAll())
  }

  handleClearFiltersClick() {
    this.store$.dispatch(new ClearFilters())
  }

  handleLibrarySectionClick(section: string): void {
    this.currSection = section
  }

  get currSectionIsLibrary(): boolean {
    return this.currSection === 'library'
  }

  get currSectionIsSettings(): boolean {
    return this.currSection === 'settings'
  }
}
