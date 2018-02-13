package ncadvanced2018.groupeone.parent.authorization;

import ncadvanced2018.groupeone.parent.authorization.model.box.BoxedAuthentication;
import ncadvanced2018.groupeone.parent.authorization.service.TokenMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends GenericFilterBean {

    private TokenMaker tokenMaker;

    @Autowired
    public AuthenticationTokenFilter(TokenMaker tokenMaker) {
        this.tokenMaker = tokenMaker;
    }

    private static final Integer START_TOKEN_NUMBER = 7;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(START_TOKEN_NUMBER);
            if (jwt.isEmpty()) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            UserDetails userDetails = this.tokenMaker.makeUserDetails(jwt);
            authentication = new BoxedAuthentication(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

}
