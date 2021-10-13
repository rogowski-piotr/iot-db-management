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
public class GetRolesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Role {

        private Long id;

        private String name;
    }

    public static Function<Collection<pl.piotr.iotdbmanagement.role.Role>, Iterable<Role>> entityToDtoMapper() {
        return roles -> {
            return roles.stream()
                    .map(role -> Role.builder()
                            .id(role.getId())
                            .name(role.getName())
                            .build())
                    .collect(Collectors.toList());
        };
    }
}
