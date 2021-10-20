package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;
import pl.piotr.iotdbmanagement.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RegisterUserRequest {

    @NotBlank(message = "can not be empty")
    @NotEmpty(message = "can not be empty")
    private String username;

    @NotEmpty(message = "can not be empty")
    @Email(message = "must be a valid e-mail address")
    private String email;

    @NotEmpty(message = "can not be empty")
    @Size(min = 8, message = "should have at least 8 characters")
    private String password;

    public static Function<RegisterUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build();
    }

}
