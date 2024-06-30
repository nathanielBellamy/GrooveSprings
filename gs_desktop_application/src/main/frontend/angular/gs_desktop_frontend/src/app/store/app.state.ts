import {playbackReducer} from "../features/playback/store/playback.reducer";
import {libraryReducer} from "../features/library/store/library.reducer";


export const gsState = {
  library: libraryReducer,
  playback: playbackReducer,
  // settings: settingsReducer,
}
