package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.dto.Edge;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.service.EdgeService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdgeServiceImpl implements EdgeService {

    private final MapsService mapsService;

    @Autowired
    public EdgeServiceImpl(MapsService mapsService) {
        this.mapsService = mapsService;
    }

    @Override
    public Edge getEdge(Address origin, Address destination) {
        long distanceTime = mapsService.getDistanceTime(origin, destination);
        return new Edge(origin, destination, distanceTime);
    }
}
