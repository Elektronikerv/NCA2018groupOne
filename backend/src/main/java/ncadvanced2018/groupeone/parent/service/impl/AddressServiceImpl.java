package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@PropertySource("classpath:address.properties")
public class AddressServiceImpl implements AddressService {

    @Value("${city.location}")
    private String cityLocation;

    @Override
    public String map(Address address) {
        return new StringBuilder()
                .append(address.getStreet())
                .append(", ")
                .append(address.getHouse())
                .append(", ")
                .append(cityLocation).toString();
    }
}
