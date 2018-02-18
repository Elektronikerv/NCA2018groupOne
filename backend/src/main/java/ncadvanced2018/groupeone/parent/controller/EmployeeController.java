package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.EmployeeEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private EmployeeEmailService emailService;

    @Autowired
    public EmployeeController(EmployeeEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/registration")
    public void createEmployee(@RequestBody User user) {
        emailService.sendEmail(user);
    }
}
