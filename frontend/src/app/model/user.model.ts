import {Role} from "./role.model";

export interface User {
  id: number;
  login: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  role: Role;
  managerId: any;
  registrationDate: Date;
}
