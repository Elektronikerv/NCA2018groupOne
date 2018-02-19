package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.EmployeeEmailService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private EmployeeEmailService emailService;
    private UserService userService;

    @Autowired
    public EmployeeController(EmployeeEmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/registration")
    public void createEmployee(@RequestBody User user) {
        User createdUser = userService.create(user);
        emailService.sendEmail(createdUser);
    }
}
