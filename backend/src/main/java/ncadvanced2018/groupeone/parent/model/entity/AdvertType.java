package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AdvertType {

    ADVERTISEMENT(1L, "ADVERTISEMENT", ""),
    NOTICE(2L, "NOTICE", ""),
    IMPORTANT_ANNOUNCEMENT(3L, "IMPORTANT_ANNOUNCEMENT", "");

    private Long id;
    private String name;
    private String description;

    AdvertType(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static AdvertType valueOf(Long id) {
        for (AdvertType type : AdvertType.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }


    public static Integer[] convertNamesToId(String[] advertTypes) {
        return Arrays.stream(advertTypes)
                .map(s -> valueOf(s).getId())
                .toArray(Integer[]::new);
    }

    @Override
    public String toString() {
        return name;
    }


}
