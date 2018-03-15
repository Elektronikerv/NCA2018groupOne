package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dto.*;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.ManagerService;
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

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("{managerId}")
    public ResponseEntity <List <EmpProfile>> findAllEmployeeByManager(@PathVariable Long managerId) {
        List <EmpProfile> all = managerService.findEmployeesByManagerWithCountOrders(managerId);
        return new ResponseEntity <>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("emp/{empId}")
    public ResponseEntity <List <MonthStatistic>> findLastYearEmpStatistic(@PathVariable Long empId) {
        List <MonthStatistic> all = managerService.findLastYearEmpStatistic(empId);
        return new ResponseEntity <>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("ccagent/{ccagentId}/ccagent/orders")
    public ResponseEntity <Long> findOrdersCountByCCAgentInCurrentMonth(@PathVariable Long ccagentId) {
        Long result = managerService.findCountOrdersByCCagentInCurrentMonth(ccagentId);
        return new ResponseEntity <>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("courier/{courierId}/courier/orders")
    public ResponseEntity <Long> findOrdersCountByCourierInCurrentMonth(@PathVariable Long courierId) {
        Long result = managerService.findCountOrdersByCourierInCurrentMonth(courierId);
        return new ResponseEntity <>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("general/ccagent")
    public ResponseEntity <GeneralStatistic> findCCAgentGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCCAgentStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("{managerId}/general/ccagent")
    public ResponseEntity <GeneralStatistic> findCCAgentGeneralStatisticByManager(@PathVariable Long managerId,
                                                                                  @RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCCAgentStatisticByManager(managerId, startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("{managerId}/personal/ccagent")
    public ResponseEntity <List <UserStatistic>> findPersonalCCAgentStatisticByManager(@PathVariable Long managerId,
                                                                                       @RequestParam("startDate") String startDate,
                                                                                       @RequestParam("endDate") String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalCCAgentStatistic(managerId, startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("general/courier")
    public ResponseEntity <GeneralStatistic> findCourierGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCourierStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("{managerId}/general/courier")
    public ResponseEntity <GeneralStatistic> findCourierGeneralStatisticByManager(@PathVariable Long managerId,
                                                                                  @RequestParam("startDate") String startDate,
                                                                                  @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findCourierStatisticByManager(managerId, startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("{managerId}/personal/courier")
    public ResponseEntity <List <UserStatistic>> findPersonalCourierStatisticByManager(@PathVariable Long managerId,
                                                                                       @RequestParam("startDate") String startDate,
                                                                                       @RequestParam("endDate") String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalCourierStatistic(managerId, startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("personal/client")
    public ResponseEntity <List <UserStatistic>> findPersonalClientStatistic(@RequestParam("startDate") String startDate,
                                                                             @RequestParam("endDate") String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalClientStatistic(startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("general/client")
    public ResponseEntity <GeneralStatistic> findClientGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                 @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findClientStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("personal/office")
    public ResponseEntity <List <OfficeStatistic>> findPersonalOfficeStatistic(@RequestParam("startDate") String startDate,
                                                                               @RequestParam("endDate") String endDate) {
        List <OfficeStatistic> generalCategoryStatistic = managerService.findPersonalOfficeStatistic(startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("general/office")
    public ResponseEntity <GeneralStatistic> findOfficeGeneralStatisticByCompany(@RequestParam("startDate") String startDate,
                                                                                 @RequestParam("endDate") String endDate) {
        GeneralStatistic generalStatistic = managerService.findOfficeStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PutMapping("/status/client")
    public ResponseEntity <List <User>> updateClientStatus(@RequestBody List <User> users) {
        List <User> userResult = managerService.updateClientRole(users);
        return new ResponseEntity <>(userResult, HttpStatus.OK);
    }

}
