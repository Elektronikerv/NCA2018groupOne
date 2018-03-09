package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    DRAFT(1L, "DRAFT", ""),
    CANCELLED(2L, "CANCELLED", ""),
    POSTPONED(3L, "POSTPONED", ""),
    ASSOCIATED(4L, "ASSOCIATED", ""),
    OPEN(5L, "OPEN", ""),
    PROCESSING(6L, "PROCESSING", ""),
    CONFIRMED(7L, "CONFIRMED", ""),
    EXECUTION(8L, "EXECUTION", ""),
    DELIVERING(9L, "DELIVERING", ""),
    DELIVERED(10L, "DELIVERED", ""),
    WAITING_FOR_FEEDBACK(11L, "WAITING_FOR_FEEDBACK", ""),
    FEEDBACK_REVIEWED(12L, "FEEDBACK_REVIEWED", "");

    private Long id;
    private String name;
    private String description;

    OrderStatus(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static OrderStatus valueOf(Long id) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getId().equals(id)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}