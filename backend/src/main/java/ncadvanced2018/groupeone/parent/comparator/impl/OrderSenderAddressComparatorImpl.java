package ncadvanced2018.groupeone.parent.comparator.impl;

import ncadvanced2018.groupeone.parent.comparator.OrderSenderAddressComparator;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderSenderAddressComparatorImpl implements OrderSenderAddressComparator {
    @Override
    public int compare(Order o1, Order o2) {
        if (Objects.nonNull(o1.getSenderAddress()) && Objects.nonNull(o2.getSenderAddress())) {
            return compareAddress(o1.getSenderAddress(), o2.getSenderAddress());
        } else if (Objects.nonNull(o1.getOffice()) && Objects.nonNull(o2.getSenderAddress())) {
            return compareAddress(o1.getOffice().getAddress(), o2.getSenderAddress());
        } else if (Objects.nonNull(o1.getSenderAddress()) && Objects.nonNull(o2.getOffice())) {
            return compareAddress(o1.getSenderAddress(), o2.getOffice().getAddress());
        } else {
            return compareAddress(o1.getOffice().getAddress(), o2.getOffice().getAddress());
        }
    }

    private int compareAddress(Address a1, Address a2) {
        if (!a1.getStreet().equals(a2.getStreet())) {
            return a1.getStreet().compareToIgnoreCase(a2.getStreet());
        } else if (!a1.getHouse().equals(a2.getHouse())) {
            return a1.getHouse().compareToIgnoreCase(a2.getHouse());
        } else if (!a1.getFloor().equals(a2.getFloor())) {
            return a1.getFloor() - a2.getFloor();
        } else {
            return a1.getFlat() - a2.getFlat();
        }
    }
}
