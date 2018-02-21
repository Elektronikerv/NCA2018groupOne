package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.SiteInformation;

public interface SiteInformationService {
    SiteInformation create(SiteInformation siteInformation);

    SiteInformation findById(Long id);

    SiteInformation update(SiteInformation siteInformation);

    boolean delete(SiteInformation siteInformation);

    boolean delete(Long id);
}
