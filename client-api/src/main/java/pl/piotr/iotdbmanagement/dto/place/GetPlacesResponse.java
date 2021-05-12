package pl.piotr.iotdbmanagement.dto.place;

import lombok.*;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetPlacesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Place {
        private Long id;

        private String description;
    }

    public static Function<Collection<pl.piotr.iotdbmanagement.entity.Place>, Iterable<GetPlacesResponse.Place>> entityToDtoMapper() {
        return places -> {
            return places.stream()
                    .map(place -> Place.builder()
                            .id(place.getId())
                            .description(place.getDescription())
                            .build())
                    .collect(Collectors.toList());
        };
    }

}
