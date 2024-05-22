import { Component  } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {TransportControlComponent} from "./transportControl/transportControl.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TransportControlComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass'
})
@Injectable()
export class AppComponent {
  title = 'gs_desktop_frontend'

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
