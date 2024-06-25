import { Component } from '@angular/core';
import {Store} from "@ngrx/store";
import {LibraryState} from "../../store/library.state";
import {ClearLibrary, LibraryScan} from "../../store/library.actions";

@Component({
  selector: 'gsSettings',
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.sass'
})
export class SettingsComponent {
  constructor(private store$: Store<{library: LibraryState}>) { }

  // TODO: input dirs to scan
  handleLibScanClick(): void {
    this.store$.dispatch(new LibraryScan())
  }

  handleLibClearClick(): void {
    this.store$.dispatch(new ClearLibrary())
  }
}
