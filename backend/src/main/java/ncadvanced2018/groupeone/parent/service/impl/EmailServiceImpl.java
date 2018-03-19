package ncadvanced2018.groupeone.parent.service.impl;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements EmailService {

    @Value("${email.smtpPort}")
    private String smtpPort;
    @Value("${email.smtpAuth}")
    private String smtpAuth;
    @Value("${email.starttls}")
    private String starttls;
    @Value("${email.emailHost}")
    private String emailHost;
    @Value("${email.fromEmail}")
    private String fromEmail;
    @Value("${email.fromPassword}")
    private String fromPassword;

    private Session mailSession;

    @PostConstruct
    public void initProperties() {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", smtpPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.host", emailHost);
        emailProperties.put("mail.smtp.starttls.enable", starttls);

        mailSession = Session.getInstance(emailProperties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, fromPassword);
                    }
                });
    }

    private MimeMessage createEmailMessage(User user, String body, String subject) throws MessagingException{
        MimeMessage emailMessage = new MimeMessage(mailSession);
        String toEmail = user.getEmail();
            emailMessage.setFrom(new InternetAddress(fromEmail));
            emailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
            emailMessage.setSubject(subject);
            emailMessage.setContent(body, "text/html");
        return  emailMessage;
    }

    public boolean sendEmail(User user, String body, String subject) {
        try {
            MimeMessage emailMessage = createEmailMessage(user, body, subject);
            Transport.send(emailMessage);
        } catch (MessagingException e) {
            log.error("Email sending failed: userId={}", user.getId(), e);
            return false;
        }
        return true;
    }

}