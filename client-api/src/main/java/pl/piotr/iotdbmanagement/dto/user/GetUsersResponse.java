package pl.piotr.iotdbmanagement.dto.user;

import lombok.*;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {
        private Long id;

        private String username;

        private String email;

        private Role role;
    }

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

    public static Function<Collection<pl.piotr.iotdbmanagement.user.User>, Iterable<User>> entityToDtoMapper() {
        return users -> {
            return users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .role(
                                    GetUsersResponse.Role.builder()
                                            .id(user.getRole().getId())
                                            .name(user.getRole().getName())
                                            .build()
                            )
                            .build())
                    .collect(Collectors.toList());
        };
    }
}
