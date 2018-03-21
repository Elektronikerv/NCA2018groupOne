package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dto.*;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import ncadvanced2018.groupeone.parent.service.WorkingDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private ManagerService managerService;
    private WorkingDayService workingDayService;

    @Autowired
    public ManagerController(ManagerService managerService, WorkingDayService workingDayService) {
        this.managerService = managerService;
        this.workingDayService = workingDayService;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("{managerId}")
    public ResponseEntity <List <EmpProfile>> findAllEmployeeByManager(@PathVariable Long managerId) {
        List <EmpProfile> all = managerService.findEmployeesByManagerWithCountOrders(managerId);
        return new ResponseEntity <>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("{managerId}/lastName/{lastName}")
    public ResponseEntity <List <EmpProfile>> findAllEmployeeByManagerAndLastName(@PathVariable Long managerId,
                                                                                  @PathVariable String lastName) {
        List <EmpProfile> all = managerService.findEmployeesByManagerAndLastNameWithCountOrders(managerId, lastName);
        return new ResponseEntity <>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("emp/{empId}")
    public ResponseEntity <List <MonthStatistic>> findLastYearEmpStatistic(@PathVariable Long empId) {
        List <MonthStatistic> all = managerService.findLastYearEmpStatistic(empId);
        return new ResponseEntity <>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("ccagent/{ccagentId}/ccagent/orders")
    public ResponseEntity <Long> findOrdersCountByCCAgentInCurrentMonth(@PathVariable Long ccagentId) {
        Long result = managerService.findCountOrdersByCCagentInCurrentMonth(ccagentId);
        return new ResponseEntity <>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("courier/{courierId}/courier/orders")
    public ResponseEntity <Long> findOrdersCountByCourierInCurrentMonth(@PathVariable Long courierId) {
        Long result = managerService.findCountOrdersByCourierInCurrentMonth(courierId);
        return new ResponseEntity <>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("general/ccagent")
    public ResponseEntity <GeneralStatistic> findCCAgentGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCCAgentStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("{managerId}/general/ccagent")
    public ResponseEntity <GeneralStatistic> findCCAgentGeneralStatisticByManager(@PathVariable Long managerId,
                                                                                  @RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCCAgentStatisticByManager(managerId, startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("{managerId}/personal/ccagent")
    public ResponseEntity <List <UserStatistic>> findPersonalCCAgentStatisticByManager(@PathVariable Long managerId,
                                                                                       @RequestParam("startDate") String startDate,
                                                                                       @RequestParam("endDate") String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalCCAgentStatistic(managerId, startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("general/courier")
    public ResponseEntity <GeneralStatistic> findCourierGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCourierStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("{managerId}/general/courier")
    public ResponseEntity <GeneralStatistic> findCourierGeneralStatisticByManager(@PathVariable Long managerId,
                                                                                  @RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCourierStatisticByManager(managerId, startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("{managerId}/personal/courier")
    public ResponseEntity <List <UserStatistic>> findPersonalCourierStatisticByManager(@PathVariable Long managerId,
                                                                                       @RequestParam("startDate") String startDate,
                                                                                       @RequestParam("endDate") String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalCourierStatistic(managerId, startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("personal/client")
    public ResponseEntity <List <UserStatistic>> findPersonalClientStatistic(@RequestParam("startDate") String startDate,
                                                                             @RequestParam("endDate") String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalClientStatistic(startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("general/client")
    public ResponseEntity <GeneralStatistic> findClientGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                 @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findClientStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("personal/office")
    public ResponseEntity <List <OfficeStatistic>> findPersonalOfficeStatistic(@RequestParam("startDate") String startDate,
                                                                               @RequestParam("endDate") String endDate) {
        List <OfficeStatistic> generalCategoryStatistic = managerService.findPersonalOfficeStatistic(startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("general/office")
    public ResponseEntity <GeneralStatistic> findOfficeGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                 @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findOfficeStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/status/client/vip")
    public ResponseEntity <List <User>> updateClientStatusToVIP(@RequestBody List <User> users) {
        List <User> userResult = managerService.updateClientRoleToVIP(users);
        return new ResponseEntity <>(userResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PutMapping("/status/client/client")
    public ResponseEntity <List <User>> updateClientStatusToClient(@RequestBody List <User> users) {
        List <User> userResult = managerService.updateClientRoleToClient(users);
        return new ResponseEntity <>(userResult, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> fetchManagersAll(){
        List<User> allManagers = managerService.findAllManagers();
        return new ResponseEntity<>(allManagers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ADMIN')")
    @GetMapping("/mgr/{employeeId}")
    public ResponseEntity<User> fetchManagersByEmployeeId(@PathVariable Long employeeId){
        User allManagers = managerService.findManagerByEmployeeId(employeeId);
        return new ResponseEntity<>(allManagers, HttpStatus.OK);
    }

    @GetMapping("month/calendar")
    public ResponseEntity <List <MonthCalendarDay>> getMonthCalendarByUser(@RequestParam("userId") Long userId) {
        System.out.println(userId);
        List <MonthCalendarDay> wDays = managerService.findMonthCalendarByUser(userId);
        return new ResponseEntity <>(wDays, HttpStatus.OK);
    }

}
