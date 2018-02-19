package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;

import java.util.List;

public interface OfficeService {
    Office create(Office office);

    Office findById(Long id);

    boolean update(Office office);

    boolean delete(Office office);

    boolean delete(Long id);

    List <Office> findByName(String name);

    List <Office> findByStreet(String street);

    List<Office> findAllWithAddress();
}
