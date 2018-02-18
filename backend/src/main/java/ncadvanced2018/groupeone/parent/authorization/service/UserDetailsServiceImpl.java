package ncadvanced2018.groupeone.parent.authorization.service;

import ncadvanced2018.groupeone.parent.authorization.model.box.BoxedUserDetails;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;


    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        if(user == null){
            throw new UsernameNotFoundException("User with email: " + s + " is not exist.");
        }
        AccountStatusUserDetailsChecker accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();
        BoxedUserDetails boxUser = new BoxedUserDetails(user);
        accountStatusUserDetailsChecker.check(boxUser);
        return boxUser;

    }
}
