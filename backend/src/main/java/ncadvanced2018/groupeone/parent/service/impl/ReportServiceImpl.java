package ncadvanced2018.groupeone.parent.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import ncadvanced2018.groupeone.parent.service.ReportService;
import ncadvanced2018.groupeone.parent.service.impl.report.ManagerReportBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ManagerReportBuilder managerReportBuilder;

    @Override
    public byte[] generateManagerPdfReport(Long id, String startDate, String endDate) {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        managerReportBuilder.setDatePeriod(startDate, endDate);
        managerReportBuilder.setManagerId(id);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            managerReportBuilder.buildPdfDocument(document);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

}
