import {Injectable} from "@angular/core";

@Injectable()
export class CustomToastService {

  message: string = null;

  constructor() {
  }

  getMessage(): string{
    return this.message;
  }

  setMessage(message: string){
    this.message = message;
  }

}
