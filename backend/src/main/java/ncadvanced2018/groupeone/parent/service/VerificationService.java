package ncadvanced2018.groupeone.parent.service;


import ncadvanced2018.groupeone.parent.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface VerificationService {
    boolean sendEmail(User user);

    void verifyEmail(User user);
}
