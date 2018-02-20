package ncadvanced2018.groupeone.parent.model.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    DRAFT(1L, "DRAFT", ""),
    CANCELLED(1L, "CANCELLED", ""),
    PROCESSING(2L, "PROCESSING", ""),
    POSTPONED(3L, "POSTPONED", ""),
    ASSOCIATED(4L, "ASSOCIATED", ""),
    CONFIRMED(5L, "CONFIRMED", ""),
    DELIVERING(6L, "DELIVERING", ""),
    DELIVERED(7L, "DELIVERED", ""),
    WAITING_FOR_FEEDBACK(8L, "WAITING_FOR_FEEDBACK", ""),
    FEEDBACK_REVIEWED(9L, "FEEDBACK_REVIEWED", "");

    private Long id;
    private String name;
    private String description;

    OrderStatus(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public static OrderStatus valueOf(Long id) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getId().equals(id)) {
                return status;
            }
        }
        return null;
    }
}