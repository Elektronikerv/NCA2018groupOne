package ncadvanced2018.groupeone.parent.entity;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Long managerId;

}
