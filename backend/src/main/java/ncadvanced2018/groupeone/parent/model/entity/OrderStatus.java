package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    DRAFT(1L, "DRAFT", ""),
    CANCELLED(2L, "CANCELLED", ""),
    PROCESSING(3L, "PROCESSING", ""),
    POSTPONED(4L, "POSTPONED", ""),
    ASSOCIATED(5L, "ASSOCIATED", ""),
    CONFIRMED(6L, "CONFIRMED", ""),
    DELIVERING(7L, "DELIVERING", ""),
    DELIVERED(8L, "DELIVERED", ""),
    WAITING_FOR_FEEDBACK(9L, "WAITING_FOR_FEEDBACK", ""),
    FEEDBACK_REVIEWED(10L, "FEEDBACK_REVIEWED", "");

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