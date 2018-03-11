package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.model.entity.FulfillmentOrder;

import java.util.List;

public interface FulfillmentOrderDao extends CrudDao <FulfillmentOrder, Long> {

//    List<FulfillmentOrder> findByCourierAndByStatus (Long courierId, Long orderStatusId);

    List<FulfillmentOrder> findByCourier(Long courierId);

    List<FulfillmentOrder> findFulfillmentForCcagent(Long ccagentId);



    FulfillmentOrder updateWithInternals(FulfillmentOrder fulfillmentOrder);

    List<CourierPoint> getCourierWay(Long courierId);

    GeneralStatistic findCCAgentStatisticByCompany(String startDate, String endDate);

    GeneralStatistic findCCAgentStatisticByManager(Long id, String startDate, String endDate);

    List <UserStatistic> findPersonalCCAgentStatisticByManager(Long id, String startDate, String endDate);

    GeneralStatistic findCourierStatisticByCompany(String startDate, String endDate);

    GeneralStatistic findCourierStatisticByManager(Long id, String startDate, String endDate);

    List <UserStatistic> findPersonalCourierStatisticByManager(Long id, String startDate, String endDate);

    Long findCountOrdersByCCagentInCurrentMonth(Long id);

    Long findCountOrdersByCourierInCurrentMonth(Long id);


    }
