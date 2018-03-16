package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import ncadvanced2018.groupeone.parent.service.PasswordRecoveryService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@PropertySource("classpath:email.properties")
public class PasswordRecoveryServiceImpl  implements PasswordRecoveryService {

    private EmailService emailService;
    private UserService userService;

    @Value("${email.recovery.body}")
    private String body;

    @Value("${email.recovery.subject}")
    private String subject;


    private final static String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static int PASSWORD_LENGTH = 12;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public PasswordRecoveryServiceImpl(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean sendEmail(User user)  {
        String newPassword = this.generateNewPassword();
        String formattedBody = body.format(body, user.getFirstName(), newPassword);

        String newEncodedPassword = passwordEncoder.encode(newPassword);
        boolean isSent = emailService.sendEmail(user, formattedBody, subject);
        if (isSent) {
            user.setPassword(newEncodedPassword);
            userService.update(user);
        }
        return isSent;
    }

    public String generateNewPassword() {
        StringBuilder newPassword = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i=0; i < PASSWORD_LENGTH; i++)
            newPassword.append(CHARS.charAt(random.nextInt(CHARS.length())));
        return newPassword.toString();
    }
}
