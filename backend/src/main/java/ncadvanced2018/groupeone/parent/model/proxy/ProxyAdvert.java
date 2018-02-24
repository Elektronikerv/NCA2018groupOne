package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.AdvertDao;
import ncadvanced2018.groupeone.parent.model.entity.Advert;
import ncadvanced2018.groupeone.parent.model.entity.AdvertType;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.time.LocalDateTime;

public class ProxyAdvert implements Advert {
    private Advert realAdvert;
    private AdvertDao dao;
    private Long id;

    public ProxyAdvert(AdvertDao dao) {
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
    public String getText() {
        return getRealAdvert().getText();
    }

    @Override
    public void setText(String text) {
        getRealAdvert().setText(text);
    }

    @Override
    public String getHeader() {
        return getRealAdvert().getHeader();
    }

    @Override
    public void setHeader(String header) {
        getRealAdvert().setHeader(header);
    }

    @Override
    public User getAdmin() {
        return getRealAdvert().getAdmin();
    }

    @Override
    public void setAdmin(User admin) {
        getRealAdvert().setAdmin(admin);
    }

    @Override
    public AdvertType getType() {
        return getRealAdvert().getType();
    }

    @Override
    public void setType(AdvertType type) {
        getRealAdvert().setType(type);
    }

    @Override
    public LocalDateTime getDateOfPublishing() {
        return getRealAdvert().getDateOfPublishing();
    }

    @Override
    public void setDateOfPublishing(LocalDateTime dateOfPublishing) {
        getRealAdvert().setDateOfPublishing(dateOfPublishing);
    }

    private Advert getRealAdvert() {
        if (realAdvert == null) {
            realAdvert = dao.findById(id);
        }
        return realAdvert;
    }


}
