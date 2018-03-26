package ncadvanced2018.groupeone.parent.dao;

import ncadvanced2018.groupeone.parent.model.entity.Advert;

import java.util.List;

public interface AdvertDao extends CrudDao<Advert, Long> {

    List<Advert> findAll();

    List<Advert> findAllSortedBy(String orderBy);

    List<Advert> findAllSortedAndFilterBy(String orderBy, String advertTypes);

    List<Advert> findAdvertsWithType(Long id);

}
