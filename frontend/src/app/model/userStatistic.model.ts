export interface UserStatistic {
  id: number;
  firstName: string;
  lastName: string;
  name: string;
  status: string;
  count: number;
  percentageByCompany: number;
  percentageByManager: number;
  differenceBetweenAvgCompany: number;
  differenceBetweenAvgManagerEmp: number;
  checked: boolean;
  isActive: boolean;
}
