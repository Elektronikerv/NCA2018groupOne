import {Role} from "./model/role.model";

export const ROLES: Role[] = [
  {id: 1, name: 'ADMIN', description: 'Administrate activities of the offices, employees and site information'},
  {id: 2, name: 'MANAGER', description: 'Monitor performance and initiate actions for strengthening results'},
  {id: 3, name: 'CALL_CENTER_AGENT', description: 'Confirm orders via telephone, interact with courier ect'},
  {id: 4, name: 'COURIER', description: 'Responsible for delivery. Respond to customer inquiries and others'},
  {id: 5, name: 'VIP_CLIENT', description: 'Client functionality + discounts and high priority of orders'},
  {id: 6, name: 'CLIENT', description: 'Order, review history of shipments, personal cabinet/profile'},
  {id: 7, name: 'UNVERIFIED_CLIENT', description: 'Client who haven\'t confirmed their account yet'}
];
