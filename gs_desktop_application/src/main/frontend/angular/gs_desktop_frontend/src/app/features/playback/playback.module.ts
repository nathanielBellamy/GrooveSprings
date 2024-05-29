import {TransportControlComponent} from "./components/transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./components/playbackDisplay/playbackDisplay.component";
import {NgModule} from "@angular/core";
import {PlaybackComponent} from "./playback.component";
import {PlaylistModule} from "./components/playlist/playlist.module";
import {StoreModule} from "@ngrx/store";
import {playbackReducer} from "./store/playback.reducer";

@NgModule({
  declarations: [PlaybackComponent],
  exports: [PlaybackComponent],
  imports: [
    StoreModule.forFeature('playback', playbackReducer),
    TransportControlComponent,
    PlaybackDisplayComponent,
    PlaylistModule
  ]

})
export class PlaybackModule {}
