package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import ncadvanced2018.groupeone.parent.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:email.properties")
public class VerificationServiceImpl implements VerificationService {

    private EmailService emailService;

    @Value("${email.verification.body}")
    private String body;

    @Value("${email.verification.subject}")
    private String subject;

    @Value("${email.url}")
    private String url;

    @Autowired
    public VerificationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public boolean sendEmail(User user) {
        String formattedBody = body.format(body, user.getFirstName(), this.createLink(user));
        return emailService.sendEmail(user, formattedBody, subject);
    }

    private String createLink(User user) {
        return url + "/verify?email=" + user.getEmail() + "&hash=" + user.getPassword();
    }
}
