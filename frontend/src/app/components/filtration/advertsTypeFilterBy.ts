import {Pipe, PipeTransform} from "@angular/core";
import {Advert} from "../../model/advert.model";

@Pipe({name: 'advertsTypeFilterBy'})
export class AdvertsTypeFilterBy implements PipeTransform {
  transform(array: Advert[], types: string[]) {
    if (types.length==0) {
      return array;
    } else {
      return array.filter(array => {
        return types.indexOf(String(array.type))!= -1;
        }
      );
    }
  }
}
