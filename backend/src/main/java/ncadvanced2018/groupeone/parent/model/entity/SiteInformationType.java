package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

@Getter
public enum SiteInformationType {

    ADVERTISEMENT(1L, "ADVERTISEMENT", ""),
    NOTICE(2L, "NOTICE", ""),
    IMPORTANT_ANNOUNCEMENT(3L, "IMPORTANT_ANNOUNCEMENT", "");

    private Long id;
    private String name;
    private String description;

    SiteInformationType(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static SiteInformationType valueOf(Long id) {
        for (SiteInformationType type : SiteInformationType.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }


}
