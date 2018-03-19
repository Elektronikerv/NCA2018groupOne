import {Pipe, PipeTransform} from "@angular/core";
import {FulfillmentOrder} from "../../../model/fulfillmentOrder.model";

@Pipe({name: 'ordersStatusFilterBy'})
export class OrderStatusFilterBy implements PipeTransform {
  transform(array: FulfillmentOrder[], statuses: string[]) {
    if (statuses.length==0) {
      return array;
    } else {
      return array.filter(fulfillmentOrder => {
          return statuses.indexOf(fulfillmentOrder.order.orderStatus)!= -1;
        }
      );
    }
  }
}
