import {Address} from "./address.model";
import {User} from "./user.model";
import {Office} from "./office.model";
import {date} from "ng2-validation/dist/date";
import {OnInit} from "@angular/core";

export class Order  implements OnInit{
  ngOnInit(): void {
    this.creationTime = new Date();
  }
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

  constructor(  id: number,
  office: Office,
  user: User,
  orderStatus: string,
  receiverAddress: Address,
  senderAddress: Address,
  creationTime: Date,
  executionTime: Date,
  receiverAvailabilityTimeFrom: Date,
  receiverAvailabilityTimeTo: Date,
  description: string,
  feedback: string){
    // this.id= id;
    // this.office= office;
    // this.user = user;
    // this.orderStatus = orderStatus;
    // this.receiverAddress = receiverAddress;
    // this.senderAddress = senderAddress;
    this.creationTime =  new Date();
    // this.executionTime = executionTime;
    // this.receiverAvailabilityTimeFrom = receiverAvailabilityTimeFrom;
    // this.receiverAvailabilityTimeTo = new Date (receiverAvailabilityTimeTo);
    // this.description = description;
    // this.feedback = feedback;
}



  set creationTime(value: Date) {
    this.creationTime = new Date();
  }
}
