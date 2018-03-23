package ncadvanced2018.groupeone.parent.controller;

import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import ncadvanced2018.groupeone.parent.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    private UserService userService;

    @Autowired
    public VerificationController(UserService userService, VerificationService verificationService) {
        this.userService = userService;
    }

    @GetMapping("/verify")
    public ResponseEntity verify(@RequestParam("email") String email, @RequestParam("hash") String encodedPassword) {
        User user = userService.findByEmail(email);
        boolean isVerified = userService.verifyEmail(user, encodedPassword);
        return isVerified ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }
}
