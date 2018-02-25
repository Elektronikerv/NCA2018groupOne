import {Role} from "./role.model";
import {Address} from "./address.model";

export interface User {
  id: number;
  login: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  roles: any[];
  managerId: any;
  address: Address;
}
