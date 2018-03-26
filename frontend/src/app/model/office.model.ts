import { Address } from "./address.model";

export interface Office {
    id: number;
    name: string;
    description: string;
    address: Address;
    isActive: Boolean;
}
