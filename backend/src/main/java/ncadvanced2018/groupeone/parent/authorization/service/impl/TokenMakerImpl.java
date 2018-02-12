package ncadvanced2018.groupeone.parent.authorization.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ncadvanced2018.groupeone.parent.authorization.model.box.BoxedUserDetails;
import ncadvanced2018.groupeone.parent.authorization.service.TokenMaker;
import ncadvanced2018.groupeone.parent.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenMakerImpl implements TokenMaker {

    private String key = "testKey";
    private UserService userService;

    @Autowired
    public TokenMakerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String makeToken(User user) {
//        LocalDateTime tokenTimeAccess = LocalDateTime.now().plus(1, ChronoUnit.HOURS);

        return Jwts.builder()
                .claim("id", user.getId())
//                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public UserDetails makeUserDetails(String jwt) {
        String uid = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody()
                .get("id")
                .toString();
        Long id = Long.parseLong(uid);
        User user = userService.getById(id);

        return new BoxedUserDetails(user);
    }

}
