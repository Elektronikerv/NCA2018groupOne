package ncadvanced2018.groupeone.parent.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import ncadvanced2018.groupeone.parent.service.ReportService;
import ncadvanced2018.groupeone.parent.service.UserService;
import ncadvanced2018.groupeone.parent.service.impl.report.ManagerReportsBuilder;
import ncadvanced2018.groupeone.parent.service.impl.report.OrderStatisticReportBuilder;
import ncadvanced2018.groupeone.parent.service.impl.report.PersonalInformationReportBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ReportServiceImpl implements ReportService {

    private ManagerReportsBuilder managerReportsBuilder;
    private PersonalInformationReportBuilder personalInformationReportBuilder;
    private OrderStatisticReportBuilder orderStatisticReportBuilder;
    private UserService userService;

    @Autowired
    public ReportServiceImpl(ManagerReportsBuilder managerReportsBuilder, PersonalInformationReportBuilder personalInformationReportBuilder,OrderStatisticReportBuilder orderStatisticReportBuilder, UserService userService) {
        this.managerReportsBuilder = managerReportsBuilder;
        this.personalInformationReportBuilder = personalInformationReportBuilder;
        this.orderStatisticReportBuilder = orderStatisticReportBuilder;
        this.userService = userService;
    }

    @Override
    public byte[] generateOfficeStatisticReport(String startDate, String endDate) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        managerReportsBuilder.setDatePeriod(startDate, endDate);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            managerReportsBuilder.buildOfficeStatisticReport(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] generateClientStatisticReport(String startDate, String endDate) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        managerReportsBuilder.setDatePeriod(startDate, endDate);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            managerReportsBuilder.buildPersonalClientStatisticReport(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] generateCCAgentStatisticReport(Long id, String startDate, String endDate) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        managerReportsBuilder.setDatePeriod(startDate, endDate);
        managerReportsBuilder.setManagerId(id);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            managerReportsBuilder.buildPersonalCCAgentReport(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] generateCourierStatisticReport(Long id, String startDate, String endDate) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        managerReportsBuilder.setDatePeriod(startDate, endDate);
        managerReportsBuilder.setManagerId(id);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            managerReportsBuilder.buildPersonalCourierStatisticReport(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] generatePersonalInformationReport(Long id) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        personalInformationReportBuilder.setUser(userService.findById(id));
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            personalInformationReportBuilder.buildPersonalInformationReport(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] generateOrderStatisticReport() {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            orderStatisticReportBuilder.buildOrderStatisticReport(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
