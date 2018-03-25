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
  creationTime: Date;
  receiverAvailabilityTimeFrom: string;
  receiverAvailabilityTimeTo: string;
  receiverAvailabilityFrom: string ;
  receiverAvailabilityTo: string ;
  receiverAvailabilityDate: string ;
  description: string;
  feedback: string;
}
