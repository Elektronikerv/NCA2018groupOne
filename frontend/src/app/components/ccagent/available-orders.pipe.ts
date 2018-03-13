import {Pipe, PipeTransform} from "@angular/core";
import {FulfillmentOrder} from "../../model/fulfillmentOrder.model";

@Pipe({
  name: 'availableOrdersPipe',
  pure: false
})
export class AvailableOrdersPipe implements PipeTransform {

  transform(items: FulfillmentOrder[], filter: any, id:number): FulfillmentOrder[] {
    if (!items || !filter) {
      return items;
    }


    return items
      .filter(item => item.ccagent == null)
      ;
  }
}
