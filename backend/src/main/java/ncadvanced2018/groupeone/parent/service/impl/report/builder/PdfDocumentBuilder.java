package ncadvanced2018.groupeone.parent.service.impl.report.builder;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PdfDocumentBuilder {

    private Document document;

    public PdfDocumentBuilder(Document document) {
        this.document = document;
        this.document.open();
    }

    public PdfDocumentBuilder addNewLine(int countLine) throws DocumentException {
        while (countLine != 0){
            document.add(new Paragraph("\n"));
            countLine--;
        }
        return this;
    }

    public PdfDocumentBuilder addLineSeparator(LineSeparator ls) throws DocumentException {
        document.add(new Chunk(ls));
        return this;
    }

    public PdfDocumentBuilder addTable(PdfPTable table) throws DocumentException {
        document.add(table);
        return this;
    }

    public PdfDocumentBuilder addParagraph(Paragraph paragraph, int alignment) throws DocumentException {
        paragraph.setAlignment(alignment);
        document.add(paragraph);
        return this;
    }

    public Document build(){
        return document;
    }
}
