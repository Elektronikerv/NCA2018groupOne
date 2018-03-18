package ncadvanced2018.groupeone.parent.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ncadvanced2018.groupeone.parent.dto.CourierPoint;
import ncadvanced2018.groupeone.parent.util.CustomDeserializer;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.List;
import java.util.Set;

@JsonDeserialize(using = CustomDeserializer.class)
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

    Address getCurrentPosition();

    void setCurrentPosition(Address address);

    Address getAddress();

    void setAddress(Address address);

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getRegistrationDate();

    void setRegistrationDate(LocalDateTime registrationDate);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);
}