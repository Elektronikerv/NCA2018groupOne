import {Address} from "./address.model";
import {OrderStatus} from "./orderStatus.model";

export interface OrderHistory {
  id: number,
  senderAddress: Address,
  receiverAddress: Address,
  creationTime: Date,
  orderStatus: OrderStatus;
  // description: string,
  // feedback : string;
}
