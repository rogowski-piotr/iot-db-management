package pl.piotr.iotdbmanagement.configuration.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.piotr.iotdbmanagement.service.UserService;
import pl.piotr.iotdbmanagement.user.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserService userService, PasswordMD5Encoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String encodedPassword = passwordEncoder.encode(password);

        User user = userService.findUserByEmail(username);
        if (user == null || !user.getPassword().equals(encodedPassword)) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
