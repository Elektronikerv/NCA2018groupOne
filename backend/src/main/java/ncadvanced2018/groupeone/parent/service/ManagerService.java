package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.*;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.util.List;

public interface ManagerService {

    GeneralStatistic findCCAgentStatisticByCompany(String startDate, String endDate);

    GeneralStatistic findCCAgentStatisticByManager(Long id, String startDate, String endDate);

    List <UserStatistic> findPersonalCCAgentStatistic(Long id, String startDate, String endDate);

    GeneralStatistic findCourierStatisticByCompany(String startDate, String endDate);

    GeneralStatistic findCourierStatisticByManager(Long id, String startDate, String endDate);

    List <UserStatistic> findPersonalCourierStatistic(Long id, String startDate, String endDate);

    GeneralStatistic findClientStatisticByCompany(String startDate, String endDate);

    List <UserStatistic> findPersonalClientStatistic(String startDate, String endDate);

    GeneralStatistic findOfficeStatisticByCompany(String startDate, String endDate);

    List <OfficeStatistic> findPersonalOfficeStatistic(String startDate, String endDate);

    Long findCountOrdersByCCagentInCurrentMonth(Long id);

    Long findCountOrdersByCourierInCurrentMonth(Long id);

    List <EmpProfile> findEmployeesByManagerWithCountOrders(Long id);

    List <User> updateClientRoleToVIP(List <User> user);

    List <User> updateClientRoleToClient(List <User> user);

    List <MonthStatistic> findLastYearEmpStatistic(Long id);
}
