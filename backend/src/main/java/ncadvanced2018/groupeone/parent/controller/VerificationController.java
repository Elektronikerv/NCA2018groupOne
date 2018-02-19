package ncadvanced2018.groupeone.parent.controller;

<<<<<<< HEAD
import ncadvanced2018.groupeone.parent.entity.User;
=======
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.EmailService;
>>>>>>> a9fd014b154a0a354e96f47cadac8dda7a60fffe
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        User user = userService.findByEmail(email);
        if(user != null && user.getPassword().equals(encodedPassword)) {
            // set user role to verified
        }
    }
}
