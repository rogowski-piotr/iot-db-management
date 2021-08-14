package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import pl.piotr.iotdbmanagement.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LoginUserRequest {

    @NotBlank(message = "can not be empty")
    @NotEmpty(message = "can not be empty")
    private String name;

    @NotEmpty(message = "can not be empty")
    private String password;

    public static Function<LoginUserRequest, Authentication> dtoToAuthMapper() {
        return request -> new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() { return null; }

            @Override
            public Object getCredentials() { return request.getPassword(); }

            @Override
            public Object getDetails() { return null; }

            @Override
            public Object getPrincipal() { return null; }

            @Override
            public boolean isAuthenticated() { return false; }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException { }

            @Override
            public String getName() { return request.getName(); }
        };
    }

}
