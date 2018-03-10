import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";

@Pipe({
  name: 'ordersOrderBy'
})
export class OrdersOrderBy implements PipeTransform {

  transform(array, orderBy, asc = true) {

    if (!orderBy || orderBy.trim() == "") {
      return array;
    }

    if (asc) {
      return Array.from(array).sort((item1: any, item2: any) => {
        return OrdersOrderBy.orderByComparator((item1['order'])[orderBy], (item2['order'])[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return OrdersOrderBy.orderByComparator((item2['order'])[orderBy], (item1['order'])[orderBy], orderBy);
      });
    }
  }

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'id' :
        return Comparators.compareNumber(a, b);
      case 'orderStatus' :
        return Comparators.compareOrderStatus(a, b);
      case 'user' :
        return Comparators.compareUserByNames(a, b);
      default:
        return 0;
    }
  }
}
