export interface EmpProfile {
  id: number;
  firstName: string;
  lastName: string;
  roles: any[];
  ccagentProcessingOrdersToday: number;
  ccagentCancelledOrConfirmedOrdersToday: number;
  courierDeliveringOrExecutionOrdersToday: number;
  courierDeliveredOrProblemOrdersToday: number;
  countWorkingDays: number;
  workingNow: boolean;
}
