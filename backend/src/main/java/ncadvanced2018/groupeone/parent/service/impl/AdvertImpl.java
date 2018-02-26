package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AdvertDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.AdvertType;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.proxy.ProxyUser;
import ncadvanced2018.groupeone.parent.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AdvertImpl implements AdvertService {

    private AdvertDao advertDao;
    private UserDao userDao;

    @Autowired
    public AdvertImpl(AdvertDao advertDao, UserDao userDao) {
        this.advertDao = advertDao;
        this.userDao = userDao;
    }

    @Override
    public Advert create(Advert advert) {
        if (advert == null) {
            log.info("SiteInformation object is null when creating");
            throw new EntityNotFoundException("SiteInformation object is null");
        }
        if (advert.getType() == null) {
            log.info("SiteInformationType object is null when creating a site information");
            throw new EntityNotFoundException("SiteInformationType object is null");
        }
        if ( advert.getAdmin() == null) {
            log.info("Admin object is null when creating a site information");
            throw new EntityNotFoundException("User object is null!!!!");
        }
        return advertDao.create(advert);
    }

    @Override
    public Advert findById(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return advertDao.findById(id);
    }

    @Override
    public List<Advert> findAll() {
        return advertDao.findAll();
    }

    @Override
    public Advert update(Advert advert) {
        if (advert == null) {
            log.info("SiteInformation object is null when updating");
            throw new EntityNotFoundException("SiteInformation object is null");
        }
        if (advert.getType() == null) {
            log.info("SiteInformationType object is null when creating a site information");
            throw new EntityNotFoundException("SiteInformationType object is null");
        }
        if ( advert.getAdmin() == null) {
            log.info("Admin object is null when creating a site information");
            throw new EntityNotFoundException("User object is null!!!!");
        }
        if (advertDao.findById(advert.getId()) == null) {
            log.info("No such site information entity");
            throw new NoSuchEntityException("SiteInformation id is not found");
        }
        return advertDao.update(advert);
    }

    @Override
    public boolean delete(Advert advert) {
        if (advert == null) {
            log.info("Office object is null when deleting");
            throw new EntityNotFoundException("SiteInformation object is null");
        }
        return advertDao.delete(advert);
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return advertDao.delete(id);
    }
}
