package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.SiteInformationDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformation;
import ncadvanced2018.groupeone.parent.service.SiteInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SiteInformationImpl implements SiteInformationService {

    private SiteInformationDao siteInformationDao;

    @Autowired
    public SiteInformationImpl(SiteInformationDao siteInformationDao) {
        this.siteInformationDao = siteInformationDao;
    }

    @Override
    public SiteInformation create(SiteInformation siteInformation) {
        if (siteInformation == null) {
            log.info("SiteInformation object is null when creating");
            throw new EntityNotFoundException("SiteInformation object is null");
        }
        return siteInformationDao.create(siteInformation);
    }

    @Override
    public SiteInformation findById(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return siteInformationDao.findById(id);
    }

    @Override
    public boolean update(SiteInformation siteInformation) {
        if (siteInformation == null) {
            log.info("SiteInformation object is null when updating");
            throw new EntityNotFoundException("SiteInformation object is null");
        }
        if (siteInformationDao.findById(siteInformation.getId()) == null) {
            log.info("No such site information entity");
            throw new NoSuchEntityException("SiteInformation id is not found");
        }
        return siteInformationDao.update(siteInformation);
    }

    @Override
    public boolean delete(SiteInformation siteInformation) {
        if (siteInformation == null) {
            log.info("Office object is null when deleting");
            throw new EntityNotFoundException("SiteInformation object is null");
        }
        return siteInformationDao.delete(siteInformation);
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return siteInformationDao.delete(id);
    }
}
