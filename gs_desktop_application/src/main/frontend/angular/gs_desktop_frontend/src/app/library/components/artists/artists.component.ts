import { Component } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AsyncPipe, NgForOf} from "@angular/common";
import { Store } from '@ngrx/store';
import {LibraryStore} from "../../store/library.reducer";
import {Observable} from "rxjs";
import {Artist} from "../../../models/artists/artists_get_all.model";
import {fetchArtists} from "../../store/library.actions";

@Component({
  selector: 'gsArtists',
  standalone: true,
  imports: [
    NgForOf,
    AsyncPipe
  ],
  templateUrl: './artists.component.html',
  styleUrl: './artists.component.sass'
})
@Injectable()
export class ArtistsComponent {
  artistCount$: Observable<number>
  artists$: Observable<Artist[]>

  constructor(private http: HttpClient, private store: Store<LibraryStore>) {
    this.artistCount$ = store.select('artistCount')
    this.artists$ = store.select('artists')
  }

  ngOnInit() {
    this.store.dispatch(fetchArtists())
  }
}
