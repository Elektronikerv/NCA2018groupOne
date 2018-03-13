package ncadvanced2018.groupeone.parent.comparator.impl;

import ncadvanced2018.groupeone.parent.comparator.FulfillmentComparator;
import ncadvanced2018.groupeone.parent.dto.Fulfillment;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Comparator;

@Component
public class FulfillmentComparatorImpl implements FulfillmentComparator {

    @Override
    public int compare(Fulfillment o1, Fulfillment o2) {
        if(o1.getClientRole() == Role.VIP_CLIENT && o2.getClientRole() == Role.CLIENT){
            return 1;
        }else if(o1.getClientRole() == Role.CLIENT && o2.getClientRole() == Role.VIP_CLIENT){
            return -1;
        }else{
            if(o1.getCreationTime().isBefore(o2.getCreationTime())){
                return 1;
            } else if(o2.getCreationTime().isBefore(o1.getCreationTime())) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
