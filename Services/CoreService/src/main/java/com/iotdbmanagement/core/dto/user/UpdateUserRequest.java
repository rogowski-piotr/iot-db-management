package com.iotdbmanagement.core.dto.user;

import lombok.*;
import com.iotdbmanagement.core.role.Role;
import com.iotdbmanagement.core.user.User;
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
