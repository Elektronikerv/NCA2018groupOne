package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

@Data
public class OfficeStatistic {
    private Long id;
    private String name;
    private Long count;
    private Double percentageByCompany;
    private Double differenceBetweenAvgCompany;
}
