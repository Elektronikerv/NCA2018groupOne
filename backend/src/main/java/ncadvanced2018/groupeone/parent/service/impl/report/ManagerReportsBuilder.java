package ncadvanced2018.groupeone.parent.service.impl.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.Getter;
import lombok.val;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfDocumentBuilder;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfPTableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.itextpdf.text.FontFactory.*;

@Service
@Getter
public class ManagerReportsBuilder {
    private Long id;
    private String start;
    private String end;

    private static int COLOR_R = 185;
    private static int COLOR_G = 247;
    private static int COLOR_B = 166;
    private static BaseColor COLOR = new BaseColor(COLOR_R, COLOR_G, COLOR_B);

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    private ManagerService managerService;

    @Autowired
    public ManagerReportsBuilder(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void setDatePeriod(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public void setManagerId(Long id) {
        this.id = id;
    }

    private PdfPTable generateOfficeStatisticsTable(String startDate, String endDate) {
        List<OfficeStatistic> officeStatistics = managerService.findPersonalOfficeStatistic(startDate, endDate);
        final int tableColumn = 5;

        PdfPTable table = new PdfPTableBuilder(tableColumn, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(COLOR, getFont(HELVETICA), "id", "Name", "Count", "Avg difference", "Percentage")
                .build();

        officeStatistics.forEach(officeStatistic -> {
            table.addCell(officeStatistic.getId().toString());
            table.addCell(officeStatistic.getName());
            table.addCell(officeStatistic.getCount().toString());
            table.addCell(officeStatistic.getDifferenceBetweenAvgCompany().toString());
            table.addCell(officeStatistic.getPercentageByCompany().toString());
        });

        return table;
    }

    private PdfPTable generatePersonalCourierStatisticTable(Long id, String startDate, String endDate) {
        List<UserStatistic> courierStatistics = managerService.findPersonalCourierStatistic(id, startDate, endDate);
        return generateUserStatisticTable(courierStatistics);
    }

    private PdfPTable generatePersonalCCAgentStatisticTable(Long id, String startDate, String endDate) {
        List<UserStatistic> ccAgentStatistics = managerService.findPersonalCCAgentStatistic(id, startDate, endDate);
        return generateUserStatisticTable(ccAgentStatistics);
    }

    private PdfPTable generatePersonalClientStatisticTable(String startDate, String endDate) {
        List<UserStatistic> clientStatistics = managerService.findPersonalClientStatistic(startDate, endDate);
        return generateUserStatisticTable(clientStatistics);
    }

    private PdfPTable generateUserStatisticTable(List<UserStatistic> statistics) {
        final int tableColumn = 6;

        PdfPTable table = new PdfPTableBuilder(tableColumn, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(COLOR, getFont(HELVETICA),
                        "id", "First name", "Last name", "Status", "Count", "Percentage By Company")
                .build();

        if (!Objects.isNull(statistics)) {
            statistics.forEach(statistic -> {
                table.addCell(statistic.getId().toString());
                table.addCell(statistic.getFirstName());
                table.addCell(statistic.getLastName());
                table.addCell(statistic.getStatus());
                table.addCell(statistic.getCount().toString());
                table.addCell(statistic.getPercentageByCompany().toString());
            });
        }

        return table;
    }

    public Document buildOfficeStatisticReport(Document document) throws DocumentException {
        val dateNow = LocalDateTime.now();
        GeneralStatistic statistic = managerService.findOfficeStatisticByCompany(start, end);

        return new PdfDocumentBuilder(document)
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("OFFICE STATISTICS REPORT"), Element.ALIGN_CENTER)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Max = " + statistic.getMax()
                        + ", Min = " + statistic.getMin()
                        + ", Count = " + statistic.getCount()
                        + ", Average = " + statistic.getAvg()), Element.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addTable(generateOfficeStatisticsTable(start, end))
                .build();
    }

    public Document buildPersonalCourierStatisticReport(Document document) throws DocumentException {
        val dateNow = LocalDateTime.now();
        GeneralStatistic statistic = managerService.findCourierStatisticByCompany(start, end);

        return new PdfDocumentBuilder(document)
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("COURIER STATISTICS REPORT"), Element.ALIGN_CENTER)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Max = " + statistic.getMax()
                        + ", Min = " + statistic.getMin()
                        + ", Count = " + statistic.getCount()
                        + ", Average = " + statistic.getAvg()), Element.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addTable(generatePersonalCourierStatisticTable(id, start, end))
                .build();
    }

    public Document buildPersonalCCAgentReport(Document document) throws DocumentException {
        val dateNow = LocalDateTime.now();
        GeneralStatistic statistic = managerService.findCourierStatisticByCompany(start, end);

        return new PdfDocumentBuilder(document)
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("CALL CENTER AGENT STATISTICS REPORT"), Element.ALIGN_CENTER)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Max = " + statistic.getMax()
                        + ", Min = " + statistic.getMin()
                        + ", Count = " + statistic.getCount()
                        + ", Average = " + statistic.getAvg()), Element.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addLineSeparator(new LineSeparator()).addTable(generatePersonalCCAgentStatisticTable(id, start, end))
                .build();
    }

    public Document buildPersonalClientStatisticReport(Document document) throws DocumentException {
        val dateNow = LocalDateTime.now();
        GeneralStatistic statistic = managerService.findClientStatisticByCompany(start, end);

        return new PdfDocumentBuilder(document)
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("CLIENT STATISTICS REPORT"), Element.ALIGN_CENTER)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Max = " + statistic.getMax()
                        + ", Min = " + statistic.getMin()
                        + ", Count = " + statistic.getCount()
                        + ", Average = " + statistic.getAvg()), Element.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addTable(generatePersonalClientStatisticTable(start, end))
                .build();
    }
}