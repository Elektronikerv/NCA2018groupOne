package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dto.EmpProfile;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("manager/{managerId}")
    public ResponseEntity <List <EmpProfile>> findAllEmployeeByManager(@PathVariable Long managerId) {
        List <EmpProfile> all = managerService.findEmployeesByManagerWithCountOrdersInCurrentMonth(managerId);
        return new ResponseEntity <>(all, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/ccagent/{ccagentId}/orders")
    public ResponseEntity <Long> findOrdersCountByCCAgentInCurrentMonth(@PathVariable Long ccagentId) {
        Long result = managerService.findCountOrdersByCCagentInCurrentMonth(ccagentId);
        return new ResponseEntity <>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/courier/{courierId}/orders")
    public ResponseEntity <Long> findOrdersCountByCourierInCurrentMonth(@PathVariable Long courierId) {
        Long result = managerService.findCountOrdersByCourierInCurrentMonth(courierId);
        return new ResponseEntity <>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/general/ccagent/company/{startDate}/{endDate}")
    public ResponseEntity <GeneralStatistic> findCCAgentGeneralStatisticByCompany(@PathVariable String startDate,
                                                                                  @PathVariable String endDate) {
        GeneralStatistic generalStatistic = managerService.findCCAgentStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/general/ccagent/{managerId}/{startDate}/{endDate}")
    public ResponseEntity <GeneralStatistic> findCCAgentGeneralStatisticByManager(@PathVariable Long managerId,
                                                                                  @PathVariable String startDate,
                                                                                  @PathVariable String endDate) {
        GeneralStatistic generalStatistic = managerService.findCCAgentStatisticByManager(managerId, startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/ccagent/{managerId}/{startDate}/{endDate}")
    public ResponseEntity <List <UserStatistic>> findPersonalCCAgentStatisticByManager(@PathVariable Long managerId,
                                                                                       @PathVariable String startDate,
                                                                                       @PathVariable String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalCCAgentStatistic(managerId, startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/general/courier/company/{startDate}/{endDate}")
    public ResponseEntity <GeneralStatistic> findCourierGeneralStatisticByCompany(@PathVariable String startDate,
                                                                                  @PathVariable String endDate) {
        GeneralStatistic generalStatistic = managerService.findCourierStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/general/courier/{managerId}/{startDate}/{endDate}")
    public ResponseEntity <GeneralStatistic> findCourierGeneralStatisticByManager(@PathVariable Long managerId,
                                                                                  @PathVariable String startDate,
                                                                                  @PathVariable String endDate) {
        GeneralStatistic generalStatistic = managerService.findCourierStatisticByManager(managerId, startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/courier/{managerId}/{startDate}/{endDate}")
    public ResponseEntity <List <UserStatistic>> findPersonalCourierStatisticByManager(@PathVariable Long managerId,
                                                                                       @PathVariable String startDate,
                                                                                       @PathVariable String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalCourierStatistic(managerId, startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/stat/client/{startDate}/{endDate}")
    public ResponseEntity <List <UserStatistic>> findPersonalClientStatistic(@PathVariable String startDate,
                                                                             @PathVariable String endDate) {
        List <UserStatistic> generalCategoryStatistic = managerService.findPersonalClientStatistic(startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/general/client/company/{startDate}/{endDate}")
    public ResponseEntity <GeneralStatistic> findClientGeneralStatisticByCompany(@PathVariable String startDate,
                                                                                 @PathVariable String endDate) {
        GeneralStatistic generalStatistic = managerService.findClientStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/stat/office/{startDate}/{endDate}")
    public ResponseEntity <List <OfficeStatistic>> findPersonalOfficeStatistic(@PathVariable String startDate,
                                                                               @PathVariable String endDate) {
        List <OfficeStatistic> generalCategoryStatistic = managerService.findPersonalOfficeStatistic(startDate, endDate);
        return new ResponseEntity <>(generalCategoryStatistic, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager/general/office/company/{startDate}/{endDate}")
    public ResponseEntity <GeneralStatistic> findOfficeGeneralStatisticByCompany(@PathVariable String startDate,
                                                                                 @PathVariable String endDate) {
        GeneralStatistic generalStatistic = managerService.findOfficeStatisticByCompany(startDate, endDate);
        return new ResponseEntity <>(generalStatistic, HttpStatus.OK);
    }

}
