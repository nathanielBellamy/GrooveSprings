import {Component, OnInit} from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {LibraryState} from "../../store/library.reducer";
import {Store} from "@ngrx/store";

@Component({
  selector: 'gsLibrary',
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
@Injectable()
export class LibraryComponent {

  constructor(private http: HttpClient, private store$: Store<{ library: LibraryState }>) { }

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
