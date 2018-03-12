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
      case 'ccagentCountOrdersMonth' :
        return Comparators.compareNumber(a, b);
      case 'courierCountOrdersMonth' :
        return Comparators.compareNumber(a, b);
      case 'ccagentCountOrdersToday' :
        return Comparators.compareNumber(a, b);
      case 'courierCountOrdersToday' :
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
