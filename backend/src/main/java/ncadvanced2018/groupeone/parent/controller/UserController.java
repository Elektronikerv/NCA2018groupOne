package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
import ncadvanced2018.groupeone.parent.service.UserService;
import ncadvanced2018.groupeone.parent.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private VerificationService verificationService;

    @Autowired
    public UserController(UserService userService,  VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) throws UnsupportedEncodingException, MessagingException {
        log.debug("test user: {}",user);
        User createdUser = userService.create(user);
        verificationService.sendEmail(createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}