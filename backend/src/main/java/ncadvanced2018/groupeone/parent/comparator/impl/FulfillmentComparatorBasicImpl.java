package ncadvanced2018.groupeone.parent.comparator.impl;

import ncadvanced2018.groupeone.parent.comparator.FulfillmentComparator;
import ncadvanced2018.groupeone.parent.dto.Fulfillment;
import org.springframework.stereotype.Component;

@Component("basic")
public class FulfillmentComparatorBasicImpl implements FulfillmentComparator {

    @Override
    public int compare(Fulfillment o1, Fulfillment o2) {

        return o1.getRowNum().compareTo( o2.getRowNum());

    }

}
