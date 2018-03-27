import {Time} from "@angular/common";

export class OrderStatisticModel{
  weekNumber: number;
  gottenOrders: number;
  processedCCA: number;
  processedCourier: number;
  cancelledOrders: number;
  avgTime: Time;
  delayTime: Time;
  lvlOfService: number;
  cancelledPercent: number;
}
