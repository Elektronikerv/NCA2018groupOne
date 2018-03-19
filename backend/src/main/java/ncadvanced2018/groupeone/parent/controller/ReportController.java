package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/officeStatisticReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getOfficeStatisticReport(@RequestParam String startDate,
                                                           @RequestParam String endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateOfficeStatisticReport(startDate, endDate), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/clientStatisticReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getClientStatisticReport(@RequestParam String startDate,
                                                           @RequestParam String endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateClientStatisticReport(startDate, endDate), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/personalCourierStatisticReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPersonalCourierStatisticReport(@RequestParam Long id,
                                                                    @RequestParam String startDate,
                                                                    @RequestParam String endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateCourierStatisticReport(id, startDate, endDate), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/personalCCAgentStatisticReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPersonalCCAgentStatisticReport(@RequestParam Long id,
                                                                    @RequestParam String startDate,
                                                                    @RequestParam String endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateCCAgentStatisticReport(id, startDate, endDate), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/personalInformationReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPersonalInformationReport(@RequestParam Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generatePersonalInformationReport(id), headers, HttpStatus.OK);
    }
}
