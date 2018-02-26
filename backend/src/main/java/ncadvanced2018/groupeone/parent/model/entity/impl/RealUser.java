package ncadvanced2018.groupeone.parent.model.entity.impl;

import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.validator.UniqueEmail;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RealUser implements User {
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @UniqueEmail
    private String email;
    private User manager;
    private Address address;
    private LocalDateTime registrationDate;
    private Set <Role> roles;
}
