package ncadvanced2018.groupeone.parent.service.impl.report.builder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.Arrays;

public class PdfPTableBuilder {
    private PdfPTable pdfPTable;

    public PdfPTableBuilder(int columns, float widthPercentage, int spacing) {
        this.pdfPTable = new PdfPTable(columns);
        this.pdfPTable.setWidthPercentage(widthPercentage);
        this.pdfPTable.setSpacingBefore(spacing);
    }

    public PdfPTableBuilder addPdfPCell(String title, BaseColor color, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(color);
        cell.setPhrase(new Phrase(title, font));
        pdfPTable.addCell(cell);
        return this;
    }

    public PdfPTableBuilder addPdfPCells(BaseColor color, Font font, String... titles) {
        Arrays.stream(titles).forEach(title -> addPdfPCell(title, color, font));
        return this;
    }

    public PdfPTableBuilder addCell(String title) {
        pdfPTable.addCell(title);
        return this;
    }

    public PdfPTableBuilder addCells(String... titles) {
        Arrays.stream(titles).forEach(this::addCell);
        return this;
    }

    public PdfPTable build() {
        return pdfPTable;
    }
}
