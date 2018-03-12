package ncadvanced2018.groupeone.parent.service.impl;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.service.AddressService;
import ncadvanced2018.groupeone.parent.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MapsServiceImpl implements MapsService {

    private GeoApiContext geoApiContext;
    private AddressService addressService;

    @Autowired
    public MapsServiceImpl(GeoApiContext geoApiContext, AddressService addressService) {
        this.geoApiContext = geoApiContext;
        this.addressService = addressService;
    }

    @Override
    public long getDistance(Address origin, Address destination) {
        DistanceMatrixElement distanceMatrixElement = getDistanceMatrixElement(origin, destination);
        return distanceMatrixElement.distance.inMeters;
    }

    public long getDistanceTime(Address origin, Address destination){
        DistanceMatrixElement distanceMatrixElement = getDistanceMatrixElement(origin, destination);
        String humanReadable = distanceMatrixElement.duration.humanReadable;
        String getMinutesOnly = humanReadable.split(" ")[0];
        long minutes = Long.valueOf(getMinutesOnly);
        return minutes;
    }

    private DistanceMatrixElement getDistanceMatrixElement(Address origin, Address destination){
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(geoApiContext);
        DistanceMatrix trix = null;
        try {
            trix = req.origins(addressService.map(origin))
                    .destinations(addressService.map(destination))
                    .mode(TravelMode.DRIVING).await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return trix.rows[0].elements[0];
    }

}
