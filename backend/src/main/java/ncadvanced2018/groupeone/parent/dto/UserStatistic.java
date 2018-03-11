package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

@Data
public class UserStatistic {
    private long id;
    private String firstName;
    private String lastName;
    private String status;
    private long count;
    private double percentageByCompany;
    private double percentageByManager;
    private double differenceBetweenAvgCompany;
    private double differenceBetweenAvgManagerEmp;
}
