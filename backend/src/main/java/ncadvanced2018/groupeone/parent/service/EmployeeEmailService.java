package ncadvanced2018.groupeone.parent.service;

import ncadvanced2018.groupeone.parent.model.entity.User;

public interface EmployeeEmailService {
    boolean sendEmail(User user);
}