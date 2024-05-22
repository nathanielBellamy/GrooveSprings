import { Component } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'gsLibrary',
  standalone: true,
  imports: [],
  templateUrl: './library.component.html',
  styleUrl: './library.component.sass'
})
@Injectable()
export class LibraryComponent {

  constructor(private http: HttpClient) { }

  async handleFileInput(e: any) {
    const fakepath = (e.target || {value: ""}).value;
    const file: string = (fakepath || "").replace("C:\\fakepath\\", '');
    const fileParts: string[] = file.split('.');
    const fileName: string = fileParts[0];
    const fileAudioCodec: string = fileParts[1];

    const res = this.http.get(`api/v1/file-select?filename=${fileName}&audiocodec=${fileAudioCodec}`)
      .subscribe(body => console.dir({body}))
    console.dir(res)
  }
}
