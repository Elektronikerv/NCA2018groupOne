import {Pipe, PipeTransform} from "@angular/core";
import {Advert} from "../../model/advert.model";

@Pipe({name: 'advertsTypeFilterBy'})
export class AdvertsTypeFilterBy implements PipeTransform {
  transform(array: Advert[], advert, notice, importantAnnouncement) {
    if (!advert && !notice && !importantAnnouncement) {
      return array;
    } else {
      return array.filter(array => {
          let filter = false;
          if (advert && String(array.type) === 'ADVERTISEMENT') filter = true;
          if (notice && String(array.type) === 'NOTICE') filter = true;
          if (importantAnnouncement && String(array.type) === 'IMPORTANT_ANNOUNCEMENT') filter = true;
          return filter;
        }
      );
    }
  }
}
