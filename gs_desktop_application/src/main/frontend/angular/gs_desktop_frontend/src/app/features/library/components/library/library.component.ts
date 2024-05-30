import {Component, OnInit} from '@angular/core';
import { Injectable } from '@angular/core';
import {LibraryState} from "../../store/library.state";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {Album} from "../../../../models/albums/album.model";
import {Artist} from "../../../../models/artists/artist.model";

@Component({
  selector: 'gsLibrary',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
@Injectable()
export class LibraryComponent {

  protected artistsFilter$: Observable<Artist[]>
  protected albumsFilter$: Observable<Album[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.artistsFilter$ = store$.select(state => state.library.filters.artists)
    this.albumsFilter$ = store$.select(state =>  state.library.filters.albums)
  }

  clearArtistsFilter() {
    // TODO:
    // this.store$.dispatch(new ClearArtistsFilter())
  }

  clearAlbumsFilter() {
    // TODO:
    // this.store$.dispatch(new ClearAlbumsFilter())
  }
}
