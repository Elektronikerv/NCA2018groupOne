package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

@Data
public class UserStatistic {
    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private Long count;
    private Double percentageByCompany;
    private double percentageByManager;
    private double differenceBetweenAvgCompany;
    private double differenceBetweenAvgManagerEmp;
}
