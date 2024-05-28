import {Component, OnInit} from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {async, Observable} from "rxjs";
import {Artist} from "../../../models/artists/artists_get_all.model";
import {LibraryStore} from "../../store/library.reducer";
import {select, Store} from "@ngrx/store";

interface GsHttpResponse {
  data: any
}

@Component({
  selector: 'gsLibrary',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
@Injectable()
export class LibraryComponent implements OnInit{
  artistCount$: Observable<number> | null = null
  artists$: Observable<Artist[]> | null = null

  constructor(private http: HttpClient, private store$: Store<LibraryStore>) {}

  ngOnInit() {
    this.artistCount$ = this.store$.pipe(select(state => state.artistCount))
    this.artists$ = this.store$.pipe(select(state => state.artists))
  }

  async handleFileInput(e: any) {
    const fakepath = (e.target || {value: ""}).value;
    const file: string = (fakepath || "").replace("C:\\fakepath\\", '');
    const fileParts: string[] = file.split('.');
    const fileName: string = fileParts[0];
    const fileAudioCodec: string = fileParts[1];

    const res = this.http.get(`api/v1/file-select?filename=${fileName}&audiocodec=${fileAudioCodec}`)
      .subscribe(body => console.dir({body}))
  }
}
