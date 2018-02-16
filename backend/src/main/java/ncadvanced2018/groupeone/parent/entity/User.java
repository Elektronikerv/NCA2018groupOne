package ncadvanced2018.groupeone.parent.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private User manager;
    private Address address;
    private LocalDate registrationDate;
    private Set<Role> roles;
}
