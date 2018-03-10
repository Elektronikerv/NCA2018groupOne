import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";
import {EmployeesOrderBy} from "./employeesOrderBy";

@Pipe({
  name: 'orderHistoryOrderBy'
})
export class OrderHistoryOrderBy implements PipeTransform {

  transform(array, orderBy, asc = true) {
    if (!orderBy || orderBy.trim() == "") {
      return array;
    }
    if (asc) {
      return Array.from(array).sort((item1: any, item2: any) => {
        return OrderHistoryOrderBy.orderByComparator(item1[orderBy], item2[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return OrderHistoryOrderBy.orderByComparator(item2[orderBy], item1[orderBy], orderBy);
      });
    }
  }

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'id' :
        return Comparators.compareNumber(a, b);
      case 'senderAddress':
      case 'receiverAddress':
        return Comparators.compareAddress(a, b);
      case 'creationTime' :
        return Comparators.compareDate(a, b);
      case 'orderStatus' :
        return Comparators.compareOrderStatus(a, b);
      default:
        return 0;
    }
  }
}
