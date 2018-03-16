package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.PasswordRecoveryService;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recovery")
public class PasswordRecoveryController {

    PasswordRecoveryService recoveryService;
    UserService userService;

    @Autowired
    public PasswordRecoveryController(PasswordRecoveryService recoveryService, UserService userService) {
        this.recoveryService = recoveryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity passwordRecovery(@RequestParam String email) {
       User user = userService.findByEmail(email);
        if(user != null)
            recoveryService.sendEmail(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}
