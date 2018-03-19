import {Address} from "./address.model";
import {User} from "./user.model";
import {Office} from "./office.model";

export interface Order  {
  id: number;
  office: Office;
  user: User;
  orderStatus: string;
  receiverAddress: Address;
  senderAddress: Address;
  executionTime: Date;
  receiverAvailabilityTimeFrom: Date;
  receiverAvailabilityTimeTo: Date;
  description: string;
  feedback: string;
}
