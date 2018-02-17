package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.SiteInformationDao;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformation;
import ncadvanced2018.groupeone.parent.model.entity.SiteInformationType;
import ncadvanced2018.groupeone.parent.model.entity.User;

public class ProxySiteInformation implements SiteInformation {
    private SiteInformation realSiteInformation;
    private SiteInformationDao dao;
    private Long id;

    public ProxySiteInformation(SiteInformationDao dao) {
        this.dao = dao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public String getText() {
        return getRealSiteInformation().getText();
    }

    @Override
    public void setText(String text) {
        getRealSiteInformation().setText(text);
    }

    @Override
    public User getAdmin() {
        return getRealSiteInformation().getAdmin();
    }

    @Override
    public void setAdmin(User admin) {
        getRealSiteInformation().setAdmin(admin);
    }

    @Override
    public SiteInformationType getType() {
        return getRealSiteInformation().getType();
    }

    @Override
    public void setType(SiteInformationType type) {
        getRealSiteInformation().setType(type);
    }

    private SiteInformation getRealSiteInformation() {
        if (realSiteInformation == null) {
            realSiteInformation = dao.findById(id);
        }
        return realSiteInformation;
    }
}
