package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.Office;

import java.util.List;

public interface OfficeDao extends CrudDao <Office, Long> {
    List <Office> findByName(String name);

<<<<<<< HEAD
    List<Office> findByAddress(Address address);

    List<Office> findAll();
=======
    List <Office> findByStreet(String street);

    List <Office> findAllWithAddress();

    //boolean createWithAddress(Office office);
>>>>>>> adminFunctionalityOffices
}
