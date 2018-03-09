package ncadvanced2018.groupeone.parent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderAction {

    TAKE("TAKE"),
    GIVE("GIVE");

    private String name;

    @Override
    public String toString() {
        return name;
    }

}
