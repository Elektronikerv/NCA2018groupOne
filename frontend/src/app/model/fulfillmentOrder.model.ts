import { User } from "./user.model";
import {Order} from "./order.model";

export interface FulfillmentOrder {
  id: number;
  order: Order;
  ccagent: User;
  courier: User;
  confirmationTime: Date;
  shipping_time: Date;
  attempt: number;
}
