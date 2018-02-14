package ncadvanced2018.groupeone.parent.authorization.controller;


import ncadvanced2018.groupeone.parent.authorization.model.AuthorizationResp;
import ncadvanced2018.groupeone.parent.authorization.model.UserAuthParam;
import ncadvanced2018.groupeone.parent.authorization.service.TokenMaker;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthorizationController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private final TokenMaker tokenMaker;

    @Autowired
    public AuthorizationController(AuthenticationManager authenticationManager, UserService userService, TokenMaker tokenMaker) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenMaker = tokenMaker;
    }

    @PostMapping("/auth")
    public AuthorizationResp login(@RequestBody UserAuthParam userAuthParam) throws AuthenticationException{
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userAuthParam.getEmail(),userAuthParam.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        String token = tokenMaker.makeToken(user);
        return new AuthorizationResp(token);
    }

}
