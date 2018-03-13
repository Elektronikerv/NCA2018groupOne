import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";

@Pipe({
  name: 'empManagerOrderBy'
})
export class EmpManagerOrderBy implements PipeTransform {

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'id' :
        return Comparators.compareNumber(a, b);
      case 'firstName' :
        return Comparators.compareString(a, b);
      case 'lastName' :
        return Comparators.compareString(a, b);
      case 'roles' :
        return Comparators.compareRoles(a, b);
      case 'ccagentProcessingOrdersToday' :
        return Comparators.compareNumber(a, b);
      case 'ccagentCancelledOrConfirmedOrdersToday' :
        return Comparators.compareNumber(a, b);
      case 'courierDeliveringOrExecutionOrdersToday' :
        return Comparators.compareNumber(a, b);
      case 'courierDeliveredOrProblemOrdersToday' :
        return Comparators.compareNumber(a, b);
      case 'countWorkingDays' :
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
        return EmpManagerOrderBy.orderByComparator(item1[orderBy], item2[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return EmpManagerOrderBy.orderByComparator(item2[orderBy], item1[orderBy], orderBy);
      });
    }
  }
}
