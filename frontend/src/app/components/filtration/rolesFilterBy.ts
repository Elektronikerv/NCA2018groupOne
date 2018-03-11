import {Pipe, PipeTransform} from "@angular/core";
import {User} from "../../model/user.model";

@Pipe({name: 'rolesFilterBy'})
export class RolesFilterBy implements PipeTransform {
  transform(array: User[], roles : string[]) {
    if (roles.length == 0) {
      return array;
    } else {
      return array.filter(array => {
        let filter = false;
        array.roles.forEach(role => {
          roles.forEach(compareRole => {
            if (compareRole == role) filter = true;
          })
        });
        return filter;
      });
    }
  }
}
