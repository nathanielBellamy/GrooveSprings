import { Component } from '@angular/core';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import {Observable} from "rxjs";
import {SetArtistsFilter} from "../../store/library.actions";
import {LibraryState} from "../../store/library.state";
import {Artist} from "../../../../models/artists/artist.model";

@Component({
  selector: 'gsArtists',
  templateUrl: './artists.component.html',
  styleUrl: './artists.component.sass'
})
@Injectable()
export class ArtistsComponent {
  artistCount$: Observable<number>
  artists$: Observable<Artist[]>

  constructor(private store$: Store<{library: LibraryState}>) {
    this.artistCount$ = store$.select(state => state.library.artistCount)
    this.artists$ = store$.select(state => state.library.artists)
  }

  ngOnInit() {
  }

  handleArtistClick(artist: Artist) {
    this.store$.dispatch( new SetArtistsFilter([artist]))
  }
}
