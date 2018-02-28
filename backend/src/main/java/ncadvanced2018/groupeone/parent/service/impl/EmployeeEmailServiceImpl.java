package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import ncadvanced2018.groupeone.parent.service.EmployeeEmailService;
import ncadvanced2018.groupeone.parent.service.PasswordRecoveryService;
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
    private PasswordRecoveryService passwordService;

    @Value("${email.employee.body}")
    private String body;

    @Value("${email.employee.subject}")
    private String subject;

    @Autowired
    public EmployeeEmailServiceImpl(UserService userService, EmailService emailService, PasswordRecoveryService passwordService) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }

    @Override
    public boolean sendEmail(User user) {
        String generatedPassword = passwordService.generateNewPassword();
        String formattedBody = String.format(body, user.getFirstName(), generatedPassword);
        boolean sent = emailService.sendEmail(user, formattedBody, subject);
        if(sent) {
//            user.setEmail("employeeNCA2018groupOne@gmail.com");
            user.setPassword(generatedPassword);
            userService.update(user);
        }
        return sent;
    }
}
