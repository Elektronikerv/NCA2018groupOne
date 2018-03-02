
export interface OrderStatus {
  id: number,
  name: string,
  description: string
}


export const ORDER_STATUSES: OrderStatus[] =  [
  { id: 1, name: "DRAFT", description: "DRAFT"},
  { id: 2, name: "CANCELLED", description: "CANCELLED"},
  { id: 3, name: "POSTPONED", description: "POSTPONED"},
  { id: 4, name: "ASSOCIATED", description: "ASSOCIATED"},
  { id: 5, name: "PROCESSING", description: "PROCESSING"},
  { id: 6, name: "CONFIRMED", description: "CONFIRMED"},
  { id: 7, name: "OPEN", description: "OPEN"},
  { id: 8, name: "DELIVERING", description: "DELIVERING"},
  { id: 9, name: "DELIVERED", description: "DELIVERED"},
  { id: 10, name: "WAITING_FOR_FEEDBACK", description: "WAITING_FOR_FEEDBACK"},
  { id: 11, name: "FEEDBACK_REVIEWED", description: "FEEDBACK_REVIEWED"}
];
