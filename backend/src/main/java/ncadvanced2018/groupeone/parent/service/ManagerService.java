package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.EmpProfile;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;

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
}
