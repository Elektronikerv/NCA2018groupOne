import {Pipe, PipeTransform} from '@angular/core';


@Pipe({name: 'tm'})
export class TimeTMPipe implements PipeTransform {
  transform(time: Date): string {
    if (!time) return null;
    if (time.toString().substring(11, 16).includes(":")) {
      return time.toString().substring(11, 16);
    }
  }
}
