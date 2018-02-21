package ncadvanced2018.groupeone.parent.service;


import ncadvanced2018.groupeone.parent.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface VerificationService {
    void sendEmail(User user);
}
