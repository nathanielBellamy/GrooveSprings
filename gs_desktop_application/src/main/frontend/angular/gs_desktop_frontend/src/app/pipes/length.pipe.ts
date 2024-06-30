import {Pipe, PipeTransform} from "@angular/core";
import {Observable} from "rxjs";

@Pipe({
  name: 'length',
  standalone: true
})
export class LengthPipe implements PipeTransform {
  transform (lengthable: string | any[] | null): number {
    if (lengthable == null) return 0
    return lengthable.length
  }
}
