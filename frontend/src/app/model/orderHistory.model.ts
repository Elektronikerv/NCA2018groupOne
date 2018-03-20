import {Address} from "./address.model";
import {OrderStatus} from "./orderStatus.model";
import {Office} from "./office.model";

export interface OrderHistory {
  id: number,
  office: Office,
  senderAddress: Address,
  receiverAddress: Address,
  creationTime: Date,
  orderStatus: OrderStatus;
  // description: string,
  // feedback : string;
}
