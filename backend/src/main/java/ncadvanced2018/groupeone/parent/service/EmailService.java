package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.User;

public interface EmailService {

    boolean sendEmail(User user, String body, String subject);

}
