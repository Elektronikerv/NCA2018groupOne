package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;

public class ProxyAddress implements Address {
    private Address realAddress;
    private AddressDao dao;
    private Long id;

    public ProxyAddress(AddressDao dao) {
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
    public String getStreet() {
        return getRealAddress().getStreet();
    }

    @Override
    public void setStreet(String street) {
        getRealAddress().setStreet(street);
    }

    @Override
    public String getHouse() {
        return getRealAddress().getHouse();
    }

    @Override
    public void setHouse(String house) {
        getRealAddress().setHouse(house);
    }

    @Override
    public Integer getFloor() {
        return getRealAddress().getFloor();
    }

    @Override
    public void setFloor(Integer floor) {
        getRealAddress().setFloor(floor);
    }

    @Override
    public Integer getFlat() {
        return getRealAddress().getFlat();
    }

    @Override
    public void setFlat(Integer flat) {
        getRealAddress().setFlat(flat);
    }

    private Address getRealAddress() {
        if (realAddress == null) {
            realAddress = dao.findById(id);
        }
        return realAddress;
    }
}