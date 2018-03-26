package ncadvanced2018.groupeone.parent.authorization.model.box;

import ncadvanced2018.groupeone.parent.model.entity.Role;
import ncadvanced2018.groupeone.parent.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BoxedUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final User user;

    public BoxedUserDetails(User user) {
        this.user = user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
//        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            switch (String.valueOf(role)) {
                case "ADMIN":
                    authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    break;
                case "MANAGER":
                    authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                    break;
                case "CLIENT":
                    authList.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
                    break;
                case "COURIER":
                    authList.add(new SimpleGrantedAuthority("ROLE_COURIER"));
                    break;
                case "CALL_CENTER_AGENT":
                    authList.add(new SimpleGrantedAuthority("ROLE_CALL_CENTER_AGENT"));
                    break;
                case "VIP_CLIENT":
                    authList.add(new SimpleGrantedAuthority("ROLE_VIP_CLIENT"));
                    break;
                case "UNVERIFIED_CLIENT":
                    authList.add(new SimpleGrantedAuthority("ROLE_UNVERIFIED_CLIENT"));
                    break;
                case "DELETED":
                    authList.add(new SimpleGrantedAuthority("ROLE_DELETED"));
                    break;
                default:
                    authList.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
            }
        }
        return authList;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxedUserDetails that = (BoxedUserDetails) o;

        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public String toString() {
        return "BoxedUserDetails{" +
                "user=" + user +
                '}';
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
