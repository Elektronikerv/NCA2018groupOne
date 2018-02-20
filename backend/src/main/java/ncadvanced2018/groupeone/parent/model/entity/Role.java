package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(1L, "ADMIN", "Administrate activities of the offices, employees and site information"),
    MANAGER(2L, "MANAGER", "Monitor performance and initiate actions for strengthening results"),
    CALL_CENTER_AGENT(3L, "CALL_CENTER_AGENT", "Confirm orders via telephone, interact with courier ect."),
    COURIER(4L, "COURIER", "Responsible for delivery. Respond to customer inquiries and others"),
    VIP_CLIENT(5L, "VIP_CLIENT", "Client functionality + discounts and high priority of orders"),
    CLIENT(6L, "CLIENT", "Order, review history of shipments, personal cabinet/profile"),
    UNVERIFIED_CLIENT(7L, "UNVERIFIED_CLIENT", "Client who haven't confirmed their account yet");

    private final Long id;
    private final String name;
    private final String description;

    Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Role valueOf(Long id) {
        for (Role role : Role.values()) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        return null;
    }
}
