package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(1L, "Admin", "Administer the activities of the offices, employees"),
    MANAGER(2L, "Manager", "Monitor performance and initiate actions to strengthen results"),
    CALL_CENTER_AGENT(3L, "Call center agent", "Confirm orders via the telephone, communicate to courier ect"),
    COURIER(4L, "Courier", "Respond to customer inquiries and others"),
    VIP_CLIENT(5L, "VIP client", "Client functionality + discount and high priority of orders"),
    CLIENT(6L, "Client", "Order, review history of shipment, personal cabinet"),
    UNVERIFIED_CLIENT(7L, "Unverified client", "Client who didn't verified yet");

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
        Role role = null;
        switch (id.intValue()) {
            case 1:
                role = ADMIN;
            break;
            case 2:
                role = MANAGER;
            break;
            case 3:
                role = CALL_CENTER_AGENT;
            break;
            case 4:
                role = COURIER;
            break;
            case 5:
                role = VIP_CLIENT;
            break;
            case 6:
                role = CLIENT;
            break;
            case 7:
                role = UNVERIFIED_CLIENT;
            break;
        }
        return role;
    }
}
