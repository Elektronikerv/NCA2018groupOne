package ncadvanced2018.groupeone.parent.service.impl;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
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
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(geoApiContext);
        DistanceMatrix trix = null;
        try {
            trix = req.origins(addressService.map(origin))
                    .destinations(addressService.map(destination))
                    .mode(TravelMode.DRIVING).
                            await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trix.rows[0].elements[0].distance.inMeters;
    }
}
