package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.model.entity.Order;

import java.util.List;

public interface OrderDao extends CrudDao <Order, Long> {

    List<Order> findAllOrders();

    List <Order> findAllConfirmedOrders();
    
    List<Order> findAllOpenOrders();

    List<Order> findByUserId(Long id);

    GeneralStatistic findClientStatisticByCompany(String startDate, String endDate);

    List <UserStatistic> findPersonalClientStatistic(String startDate, String endDate);

    GeneralStatistic findOfficeStatisticByCompany(String startDate, String endDate);

    List <OfficeStatistic> findPersonalOfficeStatistic(String startDate, String endDate);


}
