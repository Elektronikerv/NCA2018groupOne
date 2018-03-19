package ncadvanced2018.groupeone.parent.service.impl.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.impl.report.builder.PdfDocumentBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Getter
@RequiredArgsConstructor
public class PersonalInformationReportBuilder {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private StringBuilder getUserAddress() {
        StringBuilder address = new StringBuilder();
        address.append(user.getAddress().getStreet())
                .append(", ")
                .append(user.getAddress().getHouse())
                .append(", ");
        if (!Objects.isNull(user.getAddress().getFlat())) {
            address.append("Flat: ")
                    .append(user.getAddress().getFlat())
                    .append(", ");
        }
        if (!Objects.isNull(user.getAddress().getFloor())) {
            address.append("Floor: ")
                    .append(user.getAddress().getFloor())
                    .append(", ");
        }
        return address;
    }

    private StringBuilder getUserManager() {
        StringBuilder manager = new StringBuilder();
        if (!Objects.isNull(user.getManager())) {
            manager.append(user.getManager().getFirstName())
                    .append(" ")
                    .append(user.getManager().getLastName())
                    .append(", ID: ")
                    .append(user.getManager().getId());
        } else {
            manager.append("-");
        }
        return manager;
    }

    private String getUserRoles() {
        return String.join(", ",
                user.getRoles().stream()
                        .sorted(Comparator.comparingLong(Role::getId))
                        .map(Role::toString)
                        .collect(Collectors.toList()));
    }

    public Document buildPersonalInformationReport(Document document) throws DocumentException {
        val dateNow = LocalDateTime.now();

        return new PdfDocumentBuilder(document)
                .addParagraph(new Paragraph("PERSONAL INFORMATION"), Element.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph(user.getFirstName() + " " + user.getLastName()), Element.ALIGN_CENTER)
                .addNewLine(1)
                .addParagraph(new Paragraph("ID: " + user.getId()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("Email: " + user.getEmail()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("Phone number: " + user.getPhoneNumber()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("Role: " + getUserRoles()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("Address: " + getUserAddress()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("Manager: " + getUserManager()), Element.ALIGN_LEFT)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Date: " + dateNow.toLocalDate().toString()), Element.ALIGN_LEFT)
                .build();
    }
}
