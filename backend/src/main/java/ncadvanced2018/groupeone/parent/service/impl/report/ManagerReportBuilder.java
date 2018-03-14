package ncadvanced2018.groupeone.parent.service.impl.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import ncadvanced2018.groupeone.parent.dto.GeneralStatistic;
import ncadvanced2018.groupeone.parent.dto.OfficeStatistic;
import ncadvanced2018.groupeone.parent.dto.UserStatistic;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfDocumentBuilder;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfPTableBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.itextpdf.text.FontFactory.*;

@Service
@Getter
@RequiredArgsConstructor
public class ManagerReportBuilder {
    private Long id;
    private String start;
    private String end;

    private final int colorR = 185;
    private final int colorG = 247;
    private final int colorB = 166;
    private final BaseColor color = new BaseColor(colorR, colorB, colorG);

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;
    private final ManagerService managerService;

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
                .addPdfPCells(color, getFont(HELVETICA), "id")
                .addPdfPCells(color, getFont(HELVETICA), "Name")
                .addPdfPCells(color, getFont(HELVETICA), "Count")
                .addPdfPCells(color, getFont(HELVETICA), "Avg difference")
                .addPdfPCells(color, getFont(HELVETICA), "Percentage")
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

    private PdfPTable generatePersonalClientStatistic(String startDate, String endDate) {
        List<UserStatistic> clientStatistics = managerService.findPersonalClientStatistic(startDate, endDate);
        return generateUserStatisticTable(clientStatistics);
    }

    private PdfPTable generatePersonalCourierStatistic(Long id, String startDate, String endDate) {
        List<UserStatistic> courierStatistics = managerService.findPersonalCourierStatistic(id, startDate, endDate);
        return generateUserStatisticTable(courierStatistics);
    }

    private PdfPTable generatePersonalCCAgentStatistic(Long id, String startDate, String endDate) {
        List<UserStatistic> ccAgentStatistics = managerService.findPersonalCCAgentStatistic(id, startDate, endDate);
        return generateUserStatisticTable(ccAgentStatistics);
    }

    private PdfPTable generateUserStatisticTable(List<UserStatistic> statistics) {
        final int tableColumn = 6;

        PdfPTable table = new PdfPTableBuilder(tableColumn, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(color, getFont(HELVETICA), "id")
                .addPdfPCells(color, getFont(HELVETICA), "First name")
                .addPdfPCells(color, getFont(HELVETICA), "Last name")
                .addPdfPCells(color, getFont(HELVETICA), "Status")
                .addPdfPCells(color, getFont(HELVETICA), "Count")
                .addPdfPCells(color, getFont(HELVETICA), "Percentage By Company")
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

    private PdfPTable generateGeneralStatisticsTable(String startDate, String endDate) {
        Map<String, GeneralStatistic> statistics = new HashMap<>();
        statistics.put("Office", managerService.findOfficeStatisticByCompany(startDate, endDate));
        statistics.put("Client", managerService.findClientStatisticByCompany(startDate, endDate));
        statistics.put("Courier", managerService.findCourierStatisticByCompany(startDate, endDate));
        statistics.put("CCAgent", managerService.findCCAgentStatisticByCompany(startDate, endDate));
        final int tableColumn = 5;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;

        PdfPTable table = new PdfPTableBuilder(tableColumn, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorB, colorG), getFont(HELVETICA), "Name")
                .addPdfPCells(new BaseColor(colorR, colorB, colorG), getFont(HELVETICA), "Max")
                .addPdfPCells(new BaseColor(colorR, colorB, colorG), getFont(HELVETICA), "Min")
                .addPdfPCells(new BaseColor(colorR, colorB, colorG), getFont(HELVETICA), "Count")
                .addPdfPCells(new BaseColor(colorR, colorB, colorG), getFont(HELVETICA), "Average")
                .build();

        statistics.keySet().forEach(key -> {
            table.addCell(key);
            table.addCell(statistics.get(key).getMax().toString());
            table.addCell(statistics.get(key).getMin().toString());
            table.addCell(statistics.get(key).getAvg().toString());
            table.addCell(statistics.get(key).getCount().toString());
        });

        return table;
    }

    public Document buildPdfDocument(Document document) throws DocumentException {

        val dateNow = LocalDateTime.now();

        return new PdfDocumentBuilder(document)
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("GENERAL REPORT"), Element.ALIGN_TOP)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("OFFICES"), Element.ALIGN_CENTER)
                .addTable(generateOfficeStatisticsTable(start, end))
                .addNewLine(1)
                .addParagraph(new Paragraph("CALL CENTER AGENT STATISTICS"), Element.ALIGN_CENTER)
                .addTable(generatePersonalCCAgentStatistic(id, start, end))
                .addNewLine(1)
                .addParagraph(new Paragraph("COURIER STATISTICS"), Element.ALIGN_CENTER)
                .addTable(generatePersonalCourierStatistic(id, start, end))
                .addNewLine(1)
                .addParagraph(new Paragraph("CLIENT STATISTICS"), Element.ALIGN_CENTER)
                .addTable(generatePersonalClientStatistic(start, end))
                .addNewLine(1)
                .addParagraph(new Paragraph("GENERAL STATISTICS"), Element.ALIGN_CENTER)
                .addTable(generateGeneralStatisticsTable(start, end))
                .build();
    }
}