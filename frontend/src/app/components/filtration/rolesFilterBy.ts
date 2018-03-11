import {Pipe, PipeTransform} from "@angular/core";
import {User} from "../../model/user.model";

@Pipe({name: 'rolesFilterBy'})
export class RolesFilterBy implements PipeTransform {
  transform(array: User[], admin, manager, ccAgent, courier) {
    if (!admin && !manager && !ccAgent && !courier) {
      return array;
    } else {
      return array.filter(array => {
        let filter = false;
        array.roles.forEach(role => {
          if (admin && role === 'ADMIN') filter = true;
          if (manager && role === 'MANAGER') filter = true;
          if (ccAgent && role === 'CALL_CENTER_AGENT') filter = true;
          if (courier && role === 'COURIER') filter = true;
        });
        return filter;
      });
    }
  }
}
