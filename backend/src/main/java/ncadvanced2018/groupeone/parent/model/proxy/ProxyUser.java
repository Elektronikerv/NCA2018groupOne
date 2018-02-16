package ncadvanced2018.groupeone.parent.model.proxy;

import ncadvanced2018.groupeone.parent.dao.UserDao;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

public class ProxyUser implements User{
    private User realUser;
    private UserDao dao;
    private Long id;

    public ProxyUser(UserDao dao) {
        this.dao = dao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return getRealUser().getPassword();
    }

    public void setPassword(String password) {
        getRealUser().setPassword(password);
    }

    public String getFirstName() {
        return getRealUser().getPassword();
    }

    public void setFirstName(String firstName) {
        getRealUser().setFirstName(firstName);
    }

    public String getLastName() {
        return getRealUser().getLastName();
    }

    public void setLastName(String lastName) {
        getRealUser().setLastName(lastName);
    }

    public String getPhoneNumber() {
        return getRealUser().getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        getRealUser().setPhoneNumber(phoneNumber);
    }

    public String getEmail() {
        return getRealUser().getEmail();
    }

    public void setEmail(String email) {
        getRealUser().setEmail(email);
    }

    public User getManager() {
        return getRealUser().getManager();
    }

    public void setManager(User manager) {
        getRealUser().setManager(manager);
    }

    public Address getAddress() {
        return getRealUser().getAddress();
    }

    public void setAddress(Address address) {
        getRealUser().setAddress(address);
    }

    public LocalDateTime getRegistrationDate() {
        return getRealUser().getRegistrationDate();
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        getRealUser().setRegistrationDate(registrationDate);
    }

    public Set<Role> getRoles() {
        return getRealUser().getRoles();
    }

    public void setRoles(Set<Role> roles) {
        getRealUser().setRoles(roles);
    }

    private User getRealUser() {
        if (realUser == null){
            realUser = dao.findById(id);
        }
        return realUser;
    }
}
