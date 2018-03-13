package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.Address;

public interface MapsService {
    long getDistance(Address origin, Address destination);

    long getDistanceTime(Address origin, Address destination);
}
