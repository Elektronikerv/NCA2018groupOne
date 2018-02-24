import {AdvertType} from "./advertType.model";
import {User} from "./user.model";

export interface Advert {
  id: number;
  header: string;
  text: string;
  admin: User;
  type: AdvertType;
  dateOfPublishing: Date;
}
