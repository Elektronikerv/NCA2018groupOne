package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.RoleDao;
import ncadvanced2018.groupeone.parent.model.entity.Role;

public class ProxyRole implements Role {
    private Role realRole;
    private RoleDao dao;
    private Long id;

    public ProxyRole(RoleDao dao) {
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
        return getRealRole().getName();
    }

    @Override
    public void setName(String name) {
        getRealRole().setName(name);
    }

    @Override
    public String getDescription() {
        return getRealRole().getDescription();
    }

    @Override
    public void setDescription(String description) {
        getRealRole().setDescription(description);
    }

    private Role getRealRole() {
        if (realRole == null) {
            realRole = dao.findById(id);
        }
        return realRole;
    }
}
