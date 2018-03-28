package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping(value = "/officeStatisticReport", produces = "application/pdf")
    public ResponseEntity<byte[]> getOfficeStatisticReport(@RequestParam String startDate,
                                                           @RequestParam String endDate) {
        return new ResponseEntity<>(reportService.generateOfficeStatisticReport(startDate, endDate), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping(value = "/clientStatisticReport", produces = "application/pdf")
    public ResponseEntity<byte[]> getClientStatisticReport(@RequestParam String startDate,
                                                           @RequestParam String endDate) {
        return new ResponseEntity<>(reportService.generateClientStatisticReport(startDate, endDate), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping(value = "/personalCourierStatisticReport", produces = "application/pdf")
    public ResponseEntity<byte[]> getPersonalCourierStatisticReport(@RequestParam Long id,
                                                                    @RequestParam String startDate,
                                                                    @RequestParam String endDate) {
        return new ResponseEntity<>(reportService.generateCourierStatisticReport(id, startDate, endDate), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping(value = "/personalCCAgentStatisticReport", produces = "application/pdf")
    public ResponseEntity<byte[]> getPersonalCCAgentStatisticReport(@RequestParam Long id,
                                                                    @RequestParam String startDate,
                                                                    @RequestParam String endDate) {
        return new ResponseEntity<>(reportService.generateCCAgentStatisticReport(id, startDate, endDate), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CALL_CENTER_AGENT','COURIER')")
    @GetMapping(value = "/personalInformationReport", produces = "application/pdf")
    public ResponseEntity<byte[]> getPersonalInformationReport(@RequestParam Long id) {

        return new ResponseEntity<>(reportService.generatePersonalInformationReport(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping(value = "/orderStatisticReport", produces = "application/pdf")
    public ResponseEntity<byte[]> getOrderStatisticReport() {
        return new ResponseEntity<>(reportService.generateOrderStatisticReport(), HttpStatus.OK);
    }
}
