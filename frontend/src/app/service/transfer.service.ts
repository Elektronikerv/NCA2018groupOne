import {Injectable, OnInit} from "@angular/core";

@Injectable()
export class TransferService implements OnInit{

  orderId: number;

  ngOnInit(): void {
  }

  setOrderId(orderId: number){
    this.orderId = orderId;
  }

  getOrderId(): number{
    return this.orderId;
  }
}
