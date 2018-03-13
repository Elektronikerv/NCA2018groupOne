package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

@Data
public class MonthStatistic {
    private int days;
    private int month;
    private int year;
    private long ccagentOrdersCount;
    private long courierOrdersCount;
}
