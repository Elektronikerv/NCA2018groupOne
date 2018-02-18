package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import ncadvanced2018.groupeone.parent.service.EmployeeEmailService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:email.properties")
public class EmployeeEmailServiceImpl implements EmployeeEmailService{

    private EmailService emailService;
    private UserService userService;

    @Value("${email.employee.body}")
    private String body;

    @Value("${email.employee.subject}")
    private String subject;

    @Autowired
    public EmployeeEmailServiceImpl(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void sendEmail(User user) {
        body = String.format(body, user.getFirstName());
        emailService.sendEmail(user, body, subject);

        user.setEmail("employeeNCA2018groupOne@gmail.com");
        user.setPassword("employee_password");
        userService.update(user);
    }
}
