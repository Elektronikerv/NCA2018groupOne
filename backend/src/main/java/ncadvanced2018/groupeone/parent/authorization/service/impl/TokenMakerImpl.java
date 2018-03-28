package ncadvanced2018.groupeone.parent.authorization.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ncadvanced2018.groupeone.parent.authorization.model.box.BoxedUserDetails;
import ncadvanced2018.groupeone.parent.authorization.service.TokenMaker;
import ncadvanced2018.groupeone.parent.model.entity.User;
import ncadvanced2018.groupeone.parent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@PropertySource("classpath:token.properties")
public class TokenMakerImpl implements TokenMaker {

    @Value("${key}")
    private String key;
    private UserService userService;

    @Autowired
    public TokenMakerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String makeToken(User user) {
        LocalDateTime tokenTimeAccess = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        Date tokenTime = Date.from(tokenTimeAccess.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .claim("id", user.getId())
                .setExpiration(tokenTime)
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
        User user = userService.findById(id);

        return new BoxedUserDetails(user);
    }

}
