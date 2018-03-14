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

import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/managerPDFReport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getManagerPDFReport(@RequestParam Long id,
                                                      @RequestParam String startDate,
                                                      @RequestParam String endDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "report.pdf";
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<>(reportService.generateManagerPdfReport(id, startDate,endDate), headers, HttpStatus.OK);
    }
}
