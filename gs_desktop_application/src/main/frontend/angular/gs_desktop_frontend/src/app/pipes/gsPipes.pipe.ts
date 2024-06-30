import {PluckPipe} from "./pluck.pipe";
import {JoinPipe} from "./join.pipe";
import {ToHHMMSSPipe} from "./toHHMMSS.pipe";
import {TrackNumberPipe} from "./trackNumber.pipe";
import {LengthPipe} from "./length.pipe";
import {ContainsByIdPipe} from "./containsById.pipe";


export const gsPipes = [
  JoinPipe,
  PluckPipe,
  ToHHMMSSPipe,
  TrackNumberPipe,
  LengthPipe,
  ContainsByIdPipe
]
