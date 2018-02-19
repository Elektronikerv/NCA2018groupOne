package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.SiteInformationTypeDao;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformationType;

public class ProxySiteInformationType implements SiteInformationType {
    private SiteInformationType realSiteInformationType;
    private SiteInformationTypeDao dao;
    private Long id;

    public ProxySiteInformationType(SiteInformationTypeDao dao) {
        this.dao = dao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return getRealSiteInformationType().getName();
    }

    @Override
    public void setName(String name) {
        getRealSiteInformationType().setName(name);
    }

    private SiteInformationType getRealSiteInformationType() {
        if (realSiteInformationType == null) {
            realSiteInformationType = dao.findById(id);
        }
        return realSiteInformationType;
    }
}
