package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.service.PasswordRecoveryService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordRecoveryController {

    PasswordRecoveryService recoveryService;
    UserService userService;

    @Autowired
    public PasswordRecoveryController(PasswordRecoveryService recoveryService, UserService userService) {
        this.recoveryService = recoveryService;
        this.userService = userService;
    }

    @GetMapping("/recovery")
    public void passwordRecovery(@RequestParam("email") String email) {
        recoveryService.sendEmail(userService.findByEmail(email));
    }

}
