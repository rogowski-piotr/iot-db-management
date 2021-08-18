package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;
import pl.piotr.iotdbmanagement.user.User;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class AuthUserResponse {

    private Long id;

    private String username;

    private String email;

    private Role role;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    private static class Role {
        Long id;

        String name;
    }

    public static Function<User, AuthUserResponse> entityToDtoMapper() {
        return user -> AuthUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(
                        Role.builder()
                                .id(user.getRole().getId())
                                .name(user.getRole().getName())
                                .build()
                )
                .build();
    }

}
