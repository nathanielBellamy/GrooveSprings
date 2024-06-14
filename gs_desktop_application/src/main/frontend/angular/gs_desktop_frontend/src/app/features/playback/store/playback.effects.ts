import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {LibraryActionTypes} from "../../library/store/library.actiontypes";
import {map} from "rxjs";
import {ClearPlaylist} from "./playback.actions";

@Injectable()
export class PlaybackEffects {

  constructor(private actions$: Actions) { }

  clearPlaylistOnLibraryClear$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearLibrary),
    map(() => new ClearPlaylist())
  ))
}
