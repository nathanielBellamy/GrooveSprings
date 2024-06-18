import {TransportControlComponent} from "./components/transportControl/transportControl.component";
import {PlaybackDisplayComponent} from "./components/playbackDisplay/playbackDisplay.component";
import {NgModule} from "@angular/core";
import {PlaybackComponent} from "./playback.component";
import {PlaylistModule} from "./components/playlist/playlist.module";
import {StoreModule} from "@ngrx/store";
import {playbackReducer} from "./store/playback.reducer";
import {PlaybackService} from "./services/playback.service";
import {PlaybackDisplayModule} from "./components/playbackDisplay/playbackDisplay.module";
import {NgIf} from "@angular/common";

@NgModule({
  declarations: [PlaybackComponent],
  exports: [PlaybackComponent],
    imports: [
        StoreModule.forFeature('playback', playbackReducer),
        TransportControlComponent,
        PlaybackDisplayModule,
        PlaylistModule,
        NgIf
    ],
  providers: [
    PlaybackService
  ]

})
export class PlaybackModule {}
