package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.service.AddressService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Override
    public String map(Address address) {
        return address.getStreet() + ", " + address.getHouse() + ", " + "Kyiv, Ukraine";
    }
}
