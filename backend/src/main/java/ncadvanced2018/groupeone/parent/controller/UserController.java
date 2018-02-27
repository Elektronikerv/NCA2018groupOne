package ncadvanced2018.groupeone.parent.controller;

import lombok.extern.slf4j.Slf4j;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.model.entity.impl.RealUser;
import ncadvanced2018.groupeone.parent.service.UserService;
import ncadvanced2018.groupeone.parent.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity <User> create(@RequestBody RealUser user) {
        log.debug("test user: {}", user);
        User createdUser = userService.create(user);
        verificationService.sendEmail(createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId){
        User userInfo = userService.findById(userId);
        return  new ResponseEntity<>(userInfo, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity <User> update(@RequestBody RealUser user) {
        log.debug("Update user info: {}", user);
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity <User> updateUserInfo(@RequestBody RealUser user) {
        log.debug("Update user info: {}", user);
        User updatedUser = userService.updateUserInfo(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}