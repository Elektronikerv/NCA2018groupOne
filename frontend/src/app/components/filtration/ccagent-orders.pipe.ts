import { Pipe, PipeTransform } from '@angular/core';
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";

@Pipe({
  name: 'ccagentOrdersPipe',
  pure: false
})
export class CcagentOrdersPipe implements PipeTransform {



  transform(items: FulfillmentOrder[], filter: any, id:number): FulfillmentOrder[] {
    if (!items || !filter) {
      return items;
    }
    return items
      .filter(item => item.ccagent != null)
      .filter(item => item.ccagent.id == id)
    ;
  }
}
