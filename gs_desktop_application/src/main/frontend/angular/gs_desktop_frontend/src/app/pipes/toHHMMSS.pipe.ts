import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'toHHMMSS',
  standalone: true
})
export class ToHHMMSSPipe implements PipeTransform {
  transform (secondsTotal: number): any {
    let hours: number | string  = Math.floor(secondsTotal / 3600)
    let minutes: number | string = Math.floor((secondsTotal - (hours * 3600)) / 60)
    let secondsRemainder: number | string = secondsTotal - (hours * 3600) - (minutes * 60)

    if (minutes < 10) {minutes = "0"+minutes}
    if (secondsRemainder < 10) {secondsRemainder = "0"+secondsRemainder}
    if (hours === 0) {
      return minutes+':'+secondsRemainder
    } else {
      if (hours   < 10) { hours   = "0"+hours }
      return hours+':'+minutes+':'+secondsRemainder
    }
  }
}
