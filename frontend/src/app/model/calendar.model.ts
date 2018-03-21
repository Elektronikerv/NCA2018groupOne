export interface Calendar {
  id: number;
  userId: number;
  day: Date;
  startWork: Date;
  endWork: Date;
  workedOut: boolean;
  isClick: boolean;
  startClicked: Date;
  endClicked: Date;
  wdId: number;
}
