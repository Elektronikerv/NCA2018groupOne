
import {AbstractControl, FormGroup} from "@angular/forms";
import {Injectable} from "@angular/core";
import {calcPossibleSecurityContexts} from "@angular/compiler/src/template_parser/binding_parser";

@Injectable()
export class DateValidatorService{

  private static minRangeHours : number = 1;

  constructor(){}

  currentDayValidator(availabilityDate: string) {

    return (group: FormGroup): {[key: string]: any} => {
      let date = group.controls[availabilityDate];
      let currentDate: Date = new Date();
      currentDate.setHours(0,0,0);
      if (new Date(date.value) <  currentDate) {
        return {
          pastDate: "Choose date starting from today"
        };
      }
      return {
      };
    }
  }

  timeFromValidator(availabilityDate: string, availabilityTimeFrom: string) {
    return (group: FormGroup): {[key: string]: any} => {
      let date = group.controls[availabilityDate];
      let availabilityFrom = group.controls[availabilityTimeFrom];
      if(availabilityFrom.value != null) {
        let currentDateTime: Date = new Date();
        let dateTimeFrom: Date = new Date(date.value);
        dateTimeFrom.setHours(this.getHours(availabilityFrom.value),this.getMinutes(availabilityFrom.value),0,0);
        if (dateTimeFrom < currentDateTime) {
          return {
            wrongTimeFrom: "Time From Can't Be In The Past"
          };
        }
      }
      return {};
    }
  }

  timeRangeValidator( availabilityTimeFrom: string, availabilityTimeTo: string) {
    return (group: FormGroup): {[key: string]: any} => {
      let availabilityFrom = group.controls[availabilityTimeFrom];
      let availabilityTo = group.controls[availabilityTimeTo];
      if(availabilityFrom.value != null && availabilityTo.value !=null) {
        let dateTimeFrom: Date = new Date();
        dateTimeFrom.setHours((this.getHours(availabilityFrom.value)+DateValidatorService.minRangeHours),
          this.getMinutes(availabilityFrom.value));
        let dateTimeTo: Date = new Date();
        dateTimeTo.setHours(this.getHours(availabilityTo.value),this.getMinutes(availabilityTo.value));
        if (dateTimeFrom > dateTimeTo) {
          return {
            wrongRange: "Range Should Be At Least 60 Minutes"
          };
        }
      }
      return {};
    }
  }

  private getHours(time : string): number{
    return Number(time.substring(0,2));
  }

  private getMinutes(time : string): number{
    return Number(time.substring(3,5));
  }

}
