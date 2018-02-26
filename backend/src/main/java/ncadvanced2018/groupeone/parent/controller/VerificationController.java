package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import ncadvanced2018.groupeone.parent.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    private UserService userService;
    private VerificationService verificationService;

    @Autowired
    public VerificationController(UserService userService, VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @GetMapping("/verify")
    public void verify(@RequestParam("email") String email, @RequestParam("hash") String encodedPassword) {
        User user = userService.findByEmail(email);
        if(user != null && encodedPassword.equals(user.getPassword()))
            verificationService.verifyEmail(user);
    }


}
