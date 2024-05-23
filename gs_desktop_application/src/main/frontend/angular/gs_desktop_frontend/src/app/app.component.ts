import { Component  } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {PlaybackComponent} from "./playback/playback.component";
import {LibraryComponent} from "./library/components/library/library.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PlaybackComponent, LibraryComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass'
})
@Injectable()
export class AppComponent {
  title = 'gs_desktop_frontend'

  constructor(private http: HttpClient) { }
}
