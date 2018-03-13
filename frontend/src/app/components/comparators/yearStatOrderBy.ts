import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";

@Pipe({
  name: 'yearStatOrderBy'
})
export class YearStatOrderBy implements PipeTransform {

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'year' :
        return Comparators.compareNumber(a, b);
      case 'month' :
        return Comparators.compareNumber(a, b);
      case 'days' :
        return Comparators.compareNumber(a, b);
      case 'ccagentCountOrders' :
        return Comparators.compareNumber(a, b);
      case 'courierCountOrders' :
        return Comparators.compareNumber(a, b);
      default:
        return 0;
    }
  }

  transform(array, orderBy, asc = true) {
    if (!orderBy || orderBy.trim() == "") {
      return array;
    }
    if (asc) {
      return Array.from(array).sort((item1: any, item2: any) => {
        return YearStatOrderBy.orderByComparator(item1[orderBy], item2[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return YearStatOrderBy.orderByComparator(item2[orderBy], item1[orderBy], orderBy);
      });
    }
  }
}
