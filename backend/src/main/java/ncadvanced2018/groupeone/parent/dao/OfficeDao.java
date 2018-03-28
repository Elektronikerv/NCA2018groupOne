package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.Office;

import java.util.List;

public interface OfficeDao extends CrudDao <Office, Long> {
    List <Office> findByName(String name);

    List <Office> findAll();

    List <Office> findAllActive();

    List <Office> findByStreet(String street);

    List <Office> findAllWithAddress();

    List<Office> findAllSorted(String orderCondition);

    List<Office> findAllSortedByAddress(String orderCondition);

}
