package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.entity.User;

public interface PasswordRecoveryService {
    void sendEmail(User user);
    String generateNewPassword();
}
