import {Address} from "./address.model";
import {OrderStatus} from "./orderStatus.model";

export interface OrderHistory {
  id: number,
  addressFrom: Address,
  addressTo: Address,
  creationTime: Date,
  orderStatus: OrderStatus;
  // description: string,
  // feedback : string;
}
