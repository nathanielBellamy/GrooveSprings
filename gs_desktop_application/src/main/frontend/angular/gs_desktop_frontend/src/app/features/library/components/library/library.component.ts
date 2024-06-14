import {Component, OnInit} from '@angular/core';
import { Injectable } from '@angular/core';
import {LibraryState} from "../../store/library.state";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Album} from "../../../../models/albums/album.model";
import {Artist} from "../../../../models/artists/artist.model";
import {FetchTracks} from "../../store/actions/tracks.actions";
import {FetchArtists} from "../../store/actions/artists.actions";
import {FetchAlbums} from "../../store/actions/albums.actions";
import {ClearAlbumsFilter, ClearArtistsFilter, ClearLibrary, LibraryScan} from "../../store/library.actions";

@Component({
  selector: 'gsLibrary',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
@Injectable()
export class LibraryComponent implements OnInit {

  protected artistsFilter$: Observable<Artist[]>
  protected albumsFilter$: Observable<Album[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.artistsFilter$ = store$.select(state => state.library.filters.artists)
    this.albumsFilter$ = store$.select(state =>  state.library.filters.albums)
  }

  ngOnInit() {
    this.store$.dispatch(new FetchAlbums())
    this.store$.dispatch(new FetchArtists())
    this.store$.dispatch(new FetchTracks())
  }

  clearArtistsFilter() {
    this.store$.dispatch(new ClearArtistsFilter())
  }

  clearAlbumsFilter() {
    this.store$.dispatch(new ClearAlbumsFilter())
  }

  // TODO: input dirs to scan
  handleLibScanClick() {
    this.store$.dispatch(new LibraryScan())
  }

  handleLibClearClick() {
    this.store$.dispatch(new ClearLibrary())
  }
}
