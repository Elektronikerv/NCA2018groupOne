package ncadvanced2018.groupeone.parent.service.impl.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import ncadvanced2018.groupeone.parent.dto.OrderStatistic;
import ncadvanced2018.groupeone.parent.service.ManagerService;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfDocumentBuilder;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfPTableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.itextpdf.text.FontFactory.HELVETICA;
import static com.itextpdf.text.FontFactory.getFont;

@Service
@Getter
@RequiredArgsConstructor
public class OrderStatisticReportBuilder {
    private static int COLOR_R = 214;
    private static int COLOR_G = 214;
    private static int COLOR_B = 194;
    private static BaseColor COLOR = new BaseColor(COLOR_R, COLOR_G, COLOR_B);

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    private ManagerService managerService;

    @Autowired
    public OrderStatisticReportBuilder(ManagerService managerService) {
        this.managerService = managerService;
    }

    private Map<Long, PdfPTable> generateOrderStatisticsTables() {
        List<OrderStatistic> orderStatistics = managerService.findOrderStatistic();
        Map<Long, PdfPTable> pdfPTables = new HashMap<>();
        final int tableColumn = 2;

        for (OrderStatistic o : orderStatistics) {

            PdfPTable table = new PdfPTableBuilder(tableColumn, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                    .addPdfPCell("Week number", COLOR, getFont(HELVETICA))
                    .addCell(o.getWeekNumber().toString())
                    .addPdfPCell("Gotten orders", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getGottenOrders()) ? o.getGottenOrders().toString() : "")
                    .addPdfPCell("Processed CCA", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getProcessedCCA()) ? o.getProcessedCCA().toString() : "")
                    .addPdfPCell("Processed couriers", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getProcessedCourier()) ? o.getProcessedCourier().toString() : "")
                    .addPdfPCell("Canceled", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getCancelledOrders()) ? o.getCancelledOrders().toString() : "")
                    .addPdfPCell("Average time of delivering", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getAvgTime()) ? o.getAvgTime().toString() : "")
                    .addPdfPCell("Delay time", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getDelayTime()) ? o.getDelayTime().toString() : "")
                    .addPdfPCell("Level of service", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getLvlOfService()) ? o.getAvgTime().toString() : "")
                    .addPdfPCell("Percent canceled", COLOR, getFont(HELVETICA))
                    .addCell(Objects.nonNull(o.getCancelledPercent()) ? o.getCancelledPercent().toString() : "")
                    .build();
            pdfPTables.put(o.getWeekNumber(), table);
        }
        return pdfPTables;
    }

    public Document buildOrderStatisticReport(Document document) throws DocumentException {
        val dateNow = LocalDateTime.now();
        PdfDocumentBuilder pdfDocumentBuilder = new PdfDocumentBuilder(document);
        Map<Long, PdfPTable> pdfPTableMap = generateOrderStatisticsTables();

        pdfDocumentBuilder
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("ORDER STATISTICS"), Element.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addNewLine(1);

        for (Long weekNumber : pdfPTableMap.keySet()) {
            pdfDocumentBuilder
                    .addParagraph(new Paragraph("WEEK #" + weekNumber), Element.ALIGN_CENTER)
                    .addTable(pdfPTableMap.get(weekNumber))
                    .addNewLine(1);
        }

        return pdfDocumentBuilder.build();
    }
}
