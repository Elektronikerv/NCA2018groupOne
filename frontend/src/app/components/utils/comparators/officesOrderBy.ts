import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";

@Pipe({
  name: 'officesOrderBy'
})
export class OfficesOrderBy implements PipeTransform {

  transform(array, orderBy, asc = true) {

    if (!orderBy || orderBy.trim() == "") {
      return array;
    }

    if (asc) {
      return Array.from(array).sort((item1: any, item2: any) => {
        return OfficesOrderBy.orderByComparator(item1[orderBy], item2[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return OfficesOrderBy.orderByComparator(item2[orderBy], item1[orderBy], orderBy);
      });
    }
  }

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'id' :
        return Comparators.compareNumber(a, b);
      case 'name' :
        return Comparators.compareString(a, b);
      case 'address':
        return Comparators.compareAddress(a, b);
      default:
        return 0;
    }
  }
}
