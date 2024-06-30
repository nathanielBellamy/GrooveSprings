import {libraryEffects} from "../features/library/store/library.effects";
import {PlaybackEffects} from "../features/playback/store/playback.effects";


export const gsEffects = [
  ...libraryEffects,
  PlaybackEffects
]
