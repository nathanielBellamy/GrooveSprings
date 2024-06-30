import {Pipe, PipeTransform} from "@angular/core";

interface hasId {
  id: number
}

@Pipe({
  name: 'containsById',
  standalone: true
})
export class ContainsByIdPipe implements PipeTransform {
  transform (arr: hasId[] | null, elem: hasId): boolean {
    if (arr === null) return false
    return arr.map(x => x.id).includes(elem.id)
  }
}
