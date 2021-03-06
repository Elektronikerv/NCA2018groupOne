import {Office} from "../../../model/office.model";
import {User} from "../../../model/user.model";
import {Address} from "../../../model/address.model";


export class Comparators {

  static compareOffice(a: Office, b: Office) {
    return a.name.localeCompare(b.name);
  }

  static compareUserByNames(a: User, b: User): number {
    return (a.firstName + a.lastName).localeCompare(b.firstName + b.lastName);
  }

  static compareUserByFirstName(aFirstName: string, bFirstName: string): number {
    return (aFirstName).localeCompare(bFirstName );
  }

  static compareUserByLastName(aLastName: string, bLastName: string): number {
    return (aLastName).localeCompare(bLastName);
  }


  static compareUserById(a: User, b: User): number {
    return a.id - b.id;
  }

  static compareAddress(a: Address, b: Address): number {
    return (a.street + a.house + a.flat + a.floor).localeCompare(b.street + b.house + b.floor + b.flat);
  }

  static compareAdvertType(a: string, b: string): number {
    let types = ['ADVERTISEMENT', 'NOTICE', 'IMPORTANT_ANNOUNCEMENT'];
    return types.indexOf(a) - types.indexOf(b)
  }

  static compareOrderStatus(a: string, b: string): number {
    let types = ['DRAFT', 'CANCELLED', 'PROCESSING','POSTPONED', 'ASSOCIATED', 'CONFIRMED','DELIVERING', 'DELIVERED',
      'WAITING_FOR_FEEDBACK','FEEDBACK_REVIEWED'];
    return types.indexOf(a) - types.indexOf(b)
  }

  static compareRoles(a: string[], b: string[]): number {
    let roles = ['ADMIN', 'MANAGER', 'CALL_CENTER_AGENT', 'COURIER'];
    return a.sort((a2, b2) => roles.indexOf(a2) - roles.indexOf(b2)).toString()
      .localeCompare(b.sort((a2, b2) => roles.indexOf(a2) - roles.indexOf(b2)).toString());
  }

  static compareString(a: string, b: string): number {
    return a.localeCompare(b);
  }

  static compareNumber(a: number, b: number): number {
    return a - b;
  }

  static compareDate(a: string, b: string): number {
    return new Date(a).getTime() - new Date(b).getTime();
  }

  static compareBoolean(a: boolean, b: boolean): number {
    let aS: string = a ? "true" : "false";
    let bS: string = b ? "true" : "false";
    return aS.localeCompare(bS);
  }
}
