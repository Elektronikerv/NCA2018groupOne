import {Role} from "./role.model";
import {Address} from "./address.model";

export interface User {
  id: number;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  address: Address;
  role: Role;
  managerId: any;
  registrationDate: Date;
}
