import { Order } from "./order.model";
import { Address } from "./address.model";

export interface CourierPoint {
    order: Order;
    address: Address;
    orderAction: string;
    time: Date;
}
