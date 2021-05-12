package pl.piotr.iotdbmanagement.dto.place;

import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlaceResponse {

    private Long id;

    private String description;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    public static Function<pl.piotr.iotdbmanagement.entity.Place, GetPlaceResponse> entityToDtoMapper() {
        return place -> GetPlaceResponse.builder()
                .id(place.getId())
                .description(place.getDescription())
                .positionX(place.getPositionX())
                .positionY(place.getPositionY())
                .positionZ(place.getPositionZ())
                .build();
    }

}
