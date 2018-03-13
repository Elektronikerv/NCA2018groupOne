package ncadvanced2018.groupeone.parent.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CcagentWorkload implements Comparable<CcagentWorkload>{

    private Long id;
    private Integer processingOrders;
    private LocalDateTime workdayEnd;

    private Integer ordersToTake = 0; //!!!!!!!!!
    private Integer ordersBeforeEndOfWorkingDay;

//    @Override
//    public int compareTo(CcagentWorkload o) {
//
//        if(orders > o.orders){
//            return 1;
//        }else if(orders < o.orders){
//            return -1;
//        }else{
//            return 0;
//        }
//    }

    @Override
    public int compareTo(CcagentWorkload c) {

        if(ordersToTake > c.ordersToTake){
            return 1;
        }else if(ordersToTake < c.ordersToTake){
            return -1;
        }else{
            return 0;
        }
    }
}
