import {Pipe, PipeTransform} from '@angular/core';


@Pipe({name: 'tm'})
export class TimeTMPipe implements PipeTransform {
  transform(time: Date): string {
    if (!time) return null;
    return time.toString().substring(11, 16);
  }
}
