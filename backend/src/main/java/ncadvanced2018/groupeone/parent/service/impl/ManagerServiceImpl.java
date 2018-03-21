package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dao.FulfillmentOrderDao;
import ncadvanced2018.groupeone.parent.dao.OrderDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.dao.WorkingDayDao;
import ncadvanced2018.groupeone.parent.dto.*;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private UserDao userDao;
    private FulfillmentOrderDao fulfillmentOrderDao;
    private OrderDao orderDao;
    private WorkingDayDao workingDayDao;

    @Autowired
    public ManagerServiceImpl(UserDao userDao, FulfillmentOrderDao fulfillmentOrderDao,
                              OrderDao orderDao, WorkingDayDao workingDayDao) {
        this.userDao = userDao;
        this.fulfillmentOrderDao = fulfillmentOrderDao;
        this.orderDao = orderDao;
        this.workingDayDao = workingDayDao;
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
        return userDao.findEmployeesByManagerWithCounts(id);
    }

    @Override
    public List <EmpProfile> findEmployeesByManagerAndLastNameWithCountOrders(Long id, String lastName) {
        return userDao.findEmployeesByManagerAndLastNameWithCounts(id, lastName);
    }

    @Override
    public List <User> updateClientRoleToVIP(List <User> userModel) {
        List <User> updatedUsers = new ArrayList <>();
        userModel.forEach(user -> {
            User realUser = userDao.findById(user.getId());
            User updatedUser = userDao.updateClientRoleToVIP(realUser);
            updatedUsers.add(updatedUser);
        });
        return updatedUsers;
    }

    @Override
    public List <MonthStatistic> findLastYearEmpStatistic(Long id) {
        return fulfillmentOrderDao.findLastYearEmpStatistic(id);
    }

    @Override
    public List <User> updateClientRoleToClient(List <User> users) {
        List <User> updatedUsers = new ArrayList <>();
        users.forEach(user -> {
            User realUser = userDao.findById(user.getId());
            User updatedUser = userDao.updateClientRoleToClient(realUser);
            updatedUsers.add(updatedUser);
        });
        return updatedUsers;
    }

    @Override
    public List<User> findAllManagers() {
        return userDao.findAllManagers();
    }

    @Override
    public User findManagerByEmployeeId(Long employeeId) {
        return userDao.findManagerByEmployeeId(employeeId);
    }

    @Override
    public List <MonthCalendarDay> findMonthCalendarByUser(Long id) {
        return workingDayDao.findMonthCalendarByUser(id);
    }
}

