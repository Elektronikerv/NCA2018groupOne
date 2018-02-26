package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;

import java.time.LocalDateTime;
import java.util.Set;

@JsonDeserialize(as = RealUser.class)
public interface User {

    Long getId();

    void setId(Long id);

    String getPassword();

    void setPassword(String password);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    String getEmail();

    void setEmail(String email);

    User getManager();

    void setManager(User manager);

    Address getAddress();

    void setAddress(Address address);

    LocalDateTime getRegistrationDate();

    void setRegistrationDate(LocalDateTime registrationDate);

    Set <Role> getRoles();

    void setRoles(Set <Role> roles);
}