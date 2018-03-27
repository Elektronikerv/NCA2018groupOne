package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    ADMIN(1L, "ADMIN", "Administrate activities of the offices, employees and site information", false),
    MANAGER(2L, "MANAGER", "Monitor performance and initiate actions for strengthening results", false),
    CALL_CENTER_AGENT(3L, "CALL_CENTER_AGENT", "Confirm orders via telephone, interact with courier ect.", false),
    COURIER(4L, "COURIER", "Responsible for delivery. Respond to customer inquiries and others", false),
    VIP_CLIENT(5L, "VIP_CLIENT", "Client functionality + discounts and high priority of orders", false),
    CLIENT(6L, "CLIENT", "Order, review history of shipments, personal cabinet/profile", false),
    UNVERIFIED_CLIENT(7L, "UNVERIFIED_CLIENT", "Client who haven't confirmed their account yet", false),
    DELETED(8L, "DELETED", "Banned/fired client/employee", false);

    private final Long id;
    private final String name;
    private final String description;
    private Boolean checked;

    Role(Long id, String name, String description, Boolean checked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.checked = checked;
    }

    public static Role valueOf(Long id) {
        for (Role role : Role.values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        return null;
    }

    public static Role valueOfName(String name) {
        for (Role role : Role.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }

    public static Long[] convertNamesToId(String[] roles) {
        return Arrays.stream(roles)
                .map(s -> valueOf(s).getId())
                .toArray(Long[]::new);
    }

    @Override
    public String toString() {
        return name;
    }
}
