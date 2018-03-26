package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.OfficeDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;

public class ProxyOffice implements Office {
    private Office realOffice;
    private OfficeDao dao;
    private Long id;

    public ProxyOffice(OfficeDao dao) {
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
    public Address getAddress() {
        return getRealOffice().getAddress();
    }

    @Override
    public void setAddress(Address address) {
        getRealOffice().setAddress(address);
    }

    @Override
    public String getName() {
        return getRealOffice().getName();
    }

    @Override
    public void setName(String name) {
        getRealOffice().setName(name);
    }

    @Override
    public String getDescription() {
        return getRealOffice().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getRealOffice().setDescription(description);
    }

    @Override
    public Boolean getIsActive() {
        return getRealOffice().getIsActive();
    }

    @Override
    public void setIsActive(Boolean isActive) {
        getRealOffice().setIsActive(isActive);
    }


    private Office getRealOffice() {
        if (realOffice == null) {
            realOffice = dao.findById(id);
        }
        return realOffice;
    }
}
