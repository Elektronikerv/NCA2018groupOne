import { Address } from "./address.model";
import { User } from "./user.model";
import { Office } from "./office.model";
import { OrderStatus } from "./orderStatus.model"; 

export interface Order {
    id: number;
    office: Office;
    user: User;
    orderStatus : string;
    receiverAddress: Address;
    senderAddress: Address;
    creationTime: Date;
    executionTime: Date;
    description: string;
    feedback: string;
}
