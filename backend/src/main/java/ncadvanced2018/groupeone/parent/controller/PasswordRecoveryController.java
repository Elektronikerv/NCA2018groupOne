package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.service.PasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordRecoveryController {

    PasswordRecoveryService recoveryService;

    @Autowired
    public PasswordRecoveryController(PasswordRecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    @GetMapping("/recovery")
    public void passwordRecovery(@RequestParam("email") String email, @RequestParam("hash") String encodedPassword) {
        //    TO-DO    redirect to user page
    }
}
