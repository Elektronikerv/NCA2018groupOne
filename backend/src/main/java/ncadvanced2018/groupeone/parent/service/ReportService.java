package ncadvanced2018.groupeone.parent.service;

public interface ReportService {
    byte [] generateManagerPdfReport(Long id, String startDate, String endDate);
}
