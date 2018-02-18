package ncadvanced2018.groupeone.parent.authorization.service;

import ncadvanced2018.groupeone.parent.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenMaker {

    String makeToken(User user);

    UserDetails makeUserDetails(String jwt);
}
