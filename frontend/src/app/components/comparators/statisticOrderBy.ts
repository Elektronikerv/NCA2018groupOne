import {Pipe, PipeTransform} from '@angular/core';
import {Comparators} from "./comparators";

@Pipe({
  name: 'statisticOrderBy'
})
export class StatisticOrderBy implements PipeTransform {

  static orderByComparator(a: any, b: any, orderBy: string): number {
    switch (orderBy) {
      case 'id' :
        return Comparators.compareNumber(a, b);
      case 'firstName' :
        return Comparators.compareString(a, b);
      case 'lastName' :
        return Comparators.compareString(a, b);
      case 'status' :
        return Comparators.compareString(a, b);
      case 'name' :
        return Comparators.compareString(a, b);
      case 'count' :
        return Comparators.compareNumber(a, b);
      case 'percentageByCompany' :
        return Comparators.compareNumber(a, b);
      case 'percentageByManager' :
        return Comparators.compareNumber(a, b);
      case 'differenceBetweenAvgCompany' :
        return Comparators.compareNumber(a, b);
      case 'differenceBetweenAvgManagerEmp' :
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
        return StatisticOrderBy.orderByComparator(item1[orderBy], item2[orderBy], orderBy);
      });
    }
    else {
      return Array.from(array).sort((item1: any, item2: any) => {
        return StatisticOrderBy.orderByComparator(item2[orderBy], item1[orderBy], orderBy);
      });
    }
  }
}
