package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    private UserService userService;

    @Autowired
    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify")
    public void verify(@RequestParam("email") String email, @RequestParam("hash") String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(user.getPassword(), encodedPassword)) {
            // set user verified value to true

        }
    }
}
