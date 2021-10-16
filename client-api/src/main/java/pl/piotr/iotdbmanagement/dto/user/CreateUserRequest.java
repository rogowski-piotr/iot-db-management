package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;
import pl.piotr.iotdbmanagement.role.Role;
import pl.piotr.iotdbmanagement.user.User;
import javax.validation.constraints.*;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    @NotBlank(message = "can not be empty")
    @NotEmpty(message = "can not be empty")
    private String username;

    @NotEmpty(message = "can not be empty")
    @Email(message = "must be a valid e-mail address")
    private String email;

    @NotNull(message = "can not be empty")
    private Long role;

    @NotEmpty(message = "can not be empty")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    public static BiFunction<CreateUserRequest, Role, User> dtoToEntityMapper() {
        return (request, role) -> User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(role)
                .build();
    }

}

