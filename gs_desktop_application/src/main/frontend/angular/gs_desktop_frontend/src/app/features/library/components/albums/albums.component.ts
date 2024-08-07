import {Component, OnInit} from '@angular/core';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import {Observable} from "rxjs";
import {LibraryState} from "../../store/library.state";
import {Album} from "../../../../models/albums/album.model";
import {SetAlbumsFilter} from "../../store/library.actions";

@Component({
  selector: 'gsAlbums',
  templateUrl: './albums.component.html',
  styleUrl: './albums.component.sass'
})
@Injectable()
export class AlbumsComponent implements OnInit {
  protected albumCount$: Observable<number>
  protected albums$: Observable<Album[]>
  protected albumsFilter$: Observable<Album[]>

  constructor(private store$: Store<{ library: LibraryState }>) {
    this.albumCount$ = store$.select(state => state.library.albumCount)
    this.albums$ = store$.select(state => state.library.albums)
    this.albumsFilter$ = store$.select(state => state.library.filters.albums)
  }

  ngOnInit() {
  }

  handleAlbumClick(album: Album) {
    this.store$.dispatch(new SetAlbumsFilter([album]))
  }
}
