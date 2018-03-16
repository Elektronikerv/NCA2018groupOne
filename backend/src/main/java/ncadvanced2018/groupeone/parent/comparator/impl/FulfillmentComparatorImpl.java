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
        int roleComparison = o1.getClientRole().getId().compareTo(o2.getClientRole().getId());
        if(roleComparison == 0){

            int creationTimeComparison = o1.getCreationTime().compareTo(o2.getCreationTime());
            System.out.println(creationTimeComparison + "  o1.getCreationTime()  :" + o1.getCreationTime()+ "  o2.getCreationTime()  :" + o2.getCreationTime());
            if (creationTimeComparison == 0) {
                return 0;
            }
            else {
                return creationTimeComparison;
            }
        }
        else {
            return roleComparison;
        }
    }

}
