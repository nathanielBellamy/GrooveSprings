import {Component} from '@angular/core'
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RouterModule} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass',
})
@Injectable()
export class AppComponent {
  title = 'gs_desktop_frontend'

  constructor(private http: HttpClient) { }
}
