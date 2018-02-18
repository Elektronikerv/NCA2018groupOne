package ncadvanced2018.groupeone.parent.model.entity;

public interface SiteInformation {

    Long getId();

    void setId(Long id);

    String getText();

    void setText(String text);

    User getAdmin();

    void setAdmin(User admin);

    SiteInformationType getType();

    void setType(SiteInformationType type);
}
