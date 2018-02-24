export interface AdvertType {
  id: number;
  name: string;
  description: string;
}


export const ADVERT_TYPES: AdvertType[] =  [
  { id: 1, name: "ADVERTISEMENT", description: ""},
  { id: 2, name: "NOTICE", description: ""},
  { id: 3, name: "IMPORTANT_ANNOUNCEMENT", description: ""}
  ];
