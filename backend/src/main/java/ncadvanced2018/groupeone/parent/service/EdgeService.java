package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.dto.Edge;
import ncadvanced2018.groupeone.parent.model.entity.Address;

public interface EdgeService {

    Edge getEdge(Address origin, Address destination);

}
