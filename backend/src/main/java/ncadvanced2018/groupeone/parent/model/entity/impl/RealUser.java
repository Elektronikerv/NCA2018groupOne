package ncadvanced2018.groupeone.parent.model.entity.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ncadvanced2018.groupeone.parent.model.entity.Address;
import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.validator.annotation.UniqueEmail;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RealUser implements User {
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String firstName;
    private String lastName;
    @NotNull
    private String phoneNumber;
    @UniqueEmail
    private String email;
    private User manager;
    private Address address;
    private Address currentPosition;
    private LocalDateTime registrationDate;
    private Set <Role> roles;
}
