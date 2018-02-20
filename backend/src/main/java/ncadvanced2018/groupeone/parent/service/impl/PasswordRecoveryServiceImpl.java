package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import ncadvanced2018.groupeone.parent.service.PasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    EmailService emailService;

    @Value("${email.recovery.body}")
    private String body;

    @Value("${email.recovery.subject}")
    private String subject;

    @Value("${email.url}")
    private String url;

    @Autowired
    public PasswordRecoveryServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendEmail(User user) {
        body = body.format(body, user.getFirstName(), this.createLink(user));
        emailService.sendEmail(user, body, subject);
    }

    private String createLink(User user) {
        return "<a href=\"" + url + "/recovery?email=" + user.getEmail()
                + "&hash=" + user.getPassword()
                + "\"> Link </a>";
    }
}
