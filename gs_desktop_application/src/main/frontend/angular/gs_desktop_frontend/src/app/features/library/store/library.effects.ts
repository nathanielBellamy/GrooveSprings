import {AlbumsEffects} from "./effects/albums.effects";
import {ArtistsEffects} from "./effects/artists.effects";
import {TracksEffects} from "./effects/tracks.effects";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {Injectable} from "@angular/core";
import {LibraryActionTypes} from "./library.actiontypes";
import {FetchTracks} from "./actions/tracks.actions";
import {map} from "rxjs";
import {FetchAll} from "./library.actions";

@Injectable()
export class LibraryEffects {

  constructor(
    private actions$: Actions
  ){ }

  fetchAll$ = createEffect(() => this.actions$.pipe(
    ofType(LibraryActionTypes.ClearArtistsFilter, LibraryActionTypes.ClearAlbumsFilter),
    map(() => new FetchAll())
  ))

}

export const libraryEffects: any[] = [LibraryEffects, AlbumsEffects, ArtistsEffects, TracksEffects]
