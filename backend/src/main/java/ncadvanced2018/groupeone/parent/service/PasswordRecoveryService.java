package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface PasswordRecoveryService {
    void sendEmail(User user);
}
