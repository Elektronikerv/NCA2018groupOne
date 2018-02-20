package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.dao.AddressDao;
import ncadvanced2018.groupeone.parent.dao.OfficeDao;
import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.exception.EntityNotFoundException;
import ncadvanced2018.groupeone.parent.exception.NoSuchEntityException;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Office;
import ncadvanced2018.groupeone.parent.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OfficeServiceImpl implements OfficeService{

    private OfficeDao officeDao;
    private AddressDao addressDao;

    @Autowired
    public OfficeServiceImpl(OfficeDao officeDao, AddressDao addressDao) {
        this.officeDao = officeDao;
        this.addressDao = addressDao;
    }

    @Override
    public Office create(Office office) {
        if (office == null){
            log.info("Office object is null when creating");
            throw new EntityNotFoundException("Office object is null");
        }
        Address address = office.getAddress();
        if(address == null){
            log.info("Address object is null when creating an office");
            throw new EntityNotFoundException("Address object is null");
        }
        address = addressDao.create(address);
        office.setAddress(address);
        return officeDao.create(office);
    }

    @Override
    public Office findById(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return officeDao.findById(id);
    }

    @Override
    public Office update(Office office) {
        if (office == null){
            log.info("Office object is null when updating");
            throw new EntityNotFoundException("Office object is null");
        }
        if (officeDao.findById(office.getId()) == null){
            log.info("No such office entity");
            throw new NoSuchEntityException("Office id is not found");
        }
        Address address = office.getAddress();
        //check?
        addressDao.update(address);
        office.setAddress(address);
        return office;
    }

    @Override
    public boolean delete(Office office) {
        if (office == null){
            log.info("Office object is null when creating");
            throw new EntityNotFoundException("Office object is null");
        }
        Address address = office.getAddress();
        addressDao.delete(address);
        return officeDao.delete(office);
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0) {
            log.info("Illegal id");
            throw new IllegalArgumentException();
        }
        return officeDao.delete(id);
    }

    @Override
    public List<Office> findByName(String name) {
        if (name == null){
            log.info("Parameter is null when finding by name");
            throw new IllegalArgumentException();
        }
        return officeDao.findByName(name);
    }

    @Override
    public List <Office> findByAddress(Address address) {
        if (address == null){
            log.info("Parameter is null when finding by address");
            throw new EntityNotFoundException("Address object is null");
        }
        return officeDao.findByAddress(address);
    }

    @Override
    public List<Office> findAll() {
        return officeDao.findAll();
    }
}
