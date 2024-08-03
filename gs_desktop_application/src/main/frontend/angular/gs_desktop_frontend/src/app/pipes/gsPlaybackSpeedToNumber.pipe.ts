import {Pipe, PipeTransform} from "@angular/core";
import {Observable} from "rxjs";

@Pipe({
  name: 'gsPlaybackSpeedToNumber',
  standalone: true
})
export class GsPlaybackSpeedToNumberPipe implements PipeTransform {
  transform (input: number | null): number {
    if (!input) return 1
    return input
  }
}
