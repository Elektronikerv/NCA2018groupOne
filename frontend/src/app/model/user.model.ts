import {Address} from "./address.model";

export interface User {
  id: number;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  roles: any[];
  managerId: any;
  registrationDate: Date;
  address: Address;
}
