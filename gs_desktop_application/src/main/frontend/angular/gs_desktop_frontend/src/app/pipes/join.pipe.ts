import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'join',
  standalone: true
})
export class JoinPipe implements PipeTransform {
  transform (input: string[], joinWith: string): string {
    return input.join(joinWith)
  }
}
