package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dto.EmpProfile;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private UserDao userDao;
    private FulfillmentOrderDao fulfillmentOrderDao;
    private OrderDao orderDao;

    @Autowired
    public ManagerServiceImpl(UserDao userDao, FulfillmentOrderDao fulfillmentOrderDao,
                              OrderDao orderDao) {
        this.userDao = userDao;
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.orderDao = orderDao;
    }

    @Override
    public GeneralStatistic findCCAgentStatisticByCompany(String startDate, String endDate) {
        return fulfillmentOrderDao.findCCAgentStatisticByCompany(startDate, endDate);
    }

    @Override
    public GeneralStatistic findCCAgentStatisticByManager(Long id, String startDate, String endDate) {
        return fulfillmentOrderDao.findCCAgentStatisticByManager(id, startDate, endDate);
    }

    @Override
    public List <UserStatistic> findPersonalCCAgentStatistic(Long id, String startDate, String endDate) {
        return fulfillmentOrderDao.findPersonalCCAgentStatisticByManager(id, startDate, endDate);
    }

    @Override
    public GeneralStatistic findCourierStatisticByCompany(String startDate, String endDate) {
        return fulfillmentOrderDao.findCourierStatisticByCompany(startDate, endDate);
    }

    @Override
    public GeneralStatistic findCourierStatisticByManager(Long id, String startDate, String endDate) {
        return fulfillmentOrderDao.findCourierStatisticByManager(id, startDate, endDate);
    }

    @Override
    public List <UserStatistic> findPersonalCourierStatistic(Long id, String startDate, String endDate) {
        return fulfillmentOrderDao.findPersonalCourierStatisticByManager(id, startDate, endDate);
    }

    @Override
    public GeneralStatistic findClientStatisticByCompany(String startDate, String endDate) {
        return orderDao.findClientStatisticByCompany(startDate, endDate);
    }

    @Override
    public List <UserStatistic> findPersonalClientStatistic(String startDate, String endDate) {
        return orderDao.findPersonalClientStatistic(startDate, endDate);
    }

    @Override
    public GeneralStatistic findOfficeStatisticByCompany(String startDate, String endDate) {
        return orderDao.findOfficeStatisticByCompany(startDate, endDate);
    }

    @Override
    public List <OfficeStatistic> findPersonalOfficeStatistic(String startDate, String endDate) {
        return orderDao.findPersonalOfficeStatistic(startDate, endDate);
    }

    @Override
    public Long findCountOrdersByCCagentInCurrentMonth(Long id) {
        return fulfillmentOrderDao.findCountOrdersByCCagentInCurrentMonth(id);
    }

    @Override
    public Long findCountOrdersByCourierInCurrentMonth(Long id) {
        return fulfillmentOrderDao.findCountOrdersByCourierInCurrentMonth(id);
    }

    @Override
    public List <EmpProfile> findEmployeesByManagerWithCountOrders(Long id) {
        return userDao.findEmployeesByManagerWithCountOrders(id);
    }
}

