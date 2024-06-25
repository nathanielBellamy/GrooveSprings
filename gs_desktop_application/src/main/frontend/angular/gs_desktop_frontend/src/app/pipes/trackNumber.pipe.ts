import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'trackNumber',
  standalone: true
})
export class TrackNumberPipe implements PipeTransform {
  transform (trackNumber: number): string {
    if (trackNumber < 0) {
      return "-"
    } else if (trackNumber < 10) {
      return "0" + trackNumber
    } else {
      return trackNumber.toString()
    }
  }
}
