package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;
import pl.piotr.iotdbmanagement.role.Role;
import pl.piotr.iotdbmanagement.user.User;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRequest {

    private String username;

    private String email;

    private Long role;

    public static BiFunction<User, Role, User> dtoToEntityUpdater() {
        return (user, role) -> {
            user.setRole(role);
            return user;
        };
    }

}
