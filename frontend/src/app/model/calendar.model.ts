export interface Calendar {
  id: number;
  userId: number;
  day: Date;
  startWork: Date;
  endWork: Date;
  workedOut: boolean;
  isClick: boolean;
  errorsMs: string[];
  isValidStart: boolean;
  isValidEnd: boolean;
  wdId: number;
}
