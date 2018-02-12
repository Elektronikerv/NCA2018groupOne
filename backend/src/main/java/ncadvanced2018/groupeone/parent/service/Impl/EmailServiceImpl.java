package ncadvanced2018.groupeone.parent.service.Impl;

import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

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

            private static final String EMAIL_SUBJECT =  "Verification";
            private static final String EMAIL_BODY = "To verify your email continue link:\n";
            private static final String URL = "";

            private Properties emailProperties;
            private Session mailSession;
            private MimeMessage emailMessage;
            private BCryptPasswordEncoder passwordEncoder;

            public EmailServiceImpl() {
                emailProperties = System.getProperties();
                emailProperties.put("mail.smtp.port", smtpPort);
                emailProperties.put("mail.smtp.auth", smtpAuth);
                emailProperties.put("mail.smtp.starttls.enable", starttls);
                passwordEncoder = new BCryptPasswordEncoder();
            }

            private void createEmailMessage(String toEmail, User user) throws MessagingException, UnsupportedEncodingException {

                mailSession = Session.getDefaultInstance(emailProperties, null);
                emailMessage = new MimeMessage(mailSession);

                emailMessage.setFrom(new InternetAddress(fromEmail));
                emailMessage.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(toEmail));

                emailMessage.setSubject(EMAIL_SUBJECT);

                String link = EMAIL_BODY  + createLink(user);
                emailMessage.setContent(link, "text/html");
                emailMessage.setText(link);
            }

            private String createLink(User user) {
                return  URL + "/verify?email=" + user.getEmail()
                        + "&hash=" + passwordEncoder.encode(user.getPassword()) ;
            }

            public void sendEmail(String toEmail, User user) throws MessagingException, UnsupportedEncodingException {
                createEmailMessage(toEmail, user);
                Transport transport = mailSession.getTransport("smtp");
                transport.connect(emailHost, fromEmail, fromPassword);
                transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
                transport.close();
            }
        }
