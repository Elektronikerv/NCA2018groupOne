package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.Advert;

import java.util.List;

public interface AdvertService {
    Advert create(Advert advert);

    Advert findById(Long id);

    public List<Advert> findAll();

    Advert update(Advert advert);

    boolean delete(Advert advert);

    boolean delete(Long id);
}
