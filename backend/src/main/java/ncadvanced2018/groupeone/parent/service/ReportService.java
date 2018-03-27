package ncadvanced2018.groupeone.parent.service;

public interface ReportService {
    byte[] generateOfficeStatisticReport(String startDate, String endDate);

    byte[] generateClientStatisticReport(String startDate, String endDate);

    byte[] generateCCAgentStatisticReport(Long id, String startDate, String endDate);

    byte[] generateCourierStatisticReport(Long id, String startDate, String endDate);

    byte[] generatePersonalInformationReport(Long id);

    byte[] generateOrderStatisticReport();
}
