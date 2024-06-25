import {PluckPipe} from "./pluck.pipe";
import {JoinPipe} from "./join.pipe";
import {ToHHMMSSPipe} from "./toHHMMSS.pipe";
import {TrackNumberPipe} from "./trackNumber.pipe";


export const gsPipes = [
  JoinPipe,
  PluckPipe,
  ToHHMMSSPipe,
  TrackNumberPipe
]
