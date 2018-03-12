package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

@Data
public class OfficeStatistic {
    private long id;
    private String name;
    private long count;
    private double percentageByCompany;
    private double differenceBetweenAvgCompany;
}
