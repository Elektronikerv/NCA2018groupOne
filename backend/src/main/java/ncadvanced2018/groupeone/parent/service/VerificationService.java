package ncadvanced2018.groupeone.parent.service;


import ncadvanced2018.groupeone.parent.entity.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
public interface VerificationService  {
    void sendEmail(User user);
}
