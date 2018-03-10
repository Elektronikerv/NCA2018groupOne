import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";

@Pipe({
  name: 'advertsOrderBy'
})
export class AdvertsOrderBy implements PipeTransform {

  transform(array, orderBy, asc = true) {

    if (!orderBy || orderBy.trim() == "") {
      return array;
    }

    if (asc) {
      return Array.from(array).sort((item1: any, item2: any) => {
        return AdvertsOrderBy.orderByComparator(item1[orderBy], item2[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return AdvertsOrderBy.orderByComparator(item2[orderBy], item1[orderBy], orderBy);
      });
    }
  }

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'id' :
        return Comparators.compareNumber(a, b);
      case 'header' :
        return Comparators.compareString(a, b);
      case 'type' :
        return Comparators.compareAdvertType(a, b);
      case 'admin':
        return Comparators.compareUserById(a, b);
      case 'dateOfPublishing':
        return Comparators.compareDate(a,b);
      default:
        return 0;
    }
  }
}
