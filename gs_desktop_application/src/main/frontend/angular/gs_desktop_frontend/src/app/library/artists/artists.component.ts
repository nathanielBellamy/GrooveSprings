import { Component } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Artist, ArtistsGetAll} from "../../models/artists/artists_get_all.model";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'gsArtists',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './artists.component.html',
  styleUrl: './artists.component.sass'
})
@Injectable()
export class ArtistsComponent {
  protected artistCount: number = 0
  protected artists: Artist[] = []

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get(`api/v1/artists`)
      .subscribe(res => {
        const artistsRes: ArtistsGetAll = res as ArtistsGetAll
        this.artistCount = artistsRes.data.count
        this.artists = artistsRes.data.artists.sort((a,b) => this.artistNameSort(a,b)) // alphabetize artists ignoring "The "
      })
  }

  artistNameSort(a: Artist, b: Artist): number {
    return this.trimArtistName(a.name).localeCompare(this.trimArtistName(b.name))
  }

  trimArtistName(artistName: string): string {
    var res: string = artistName
    if (artistName.substring(0, 4) == "The ") {
      res = artistName.replace("The ", '')
    }
    return res
  }
}
