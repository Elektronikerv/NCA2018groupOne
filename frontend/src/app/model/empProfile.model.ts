export interface EmpProfile {
  id: number;
  firstName: string;
  lastName: string;
  roles: any[];
  ccagentCountOrdersMonth: number;
  courierCountOrdersMonth: number;
  ccagentCountOrdersToday: number;
  courierCountOrdersToday: number;
  countWorkingDays: number;
}
