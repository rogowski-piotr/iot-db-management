package pl.piotr.iotdbmanagement.dto.place;

import lombok.*;
import pl.piotr.iotdbmanagement.place.Place;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlaceResponse {

    private String id;

    private String description;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    public static Function<Place, GetPlaceResponse> entityToDtoMapper() {
        return place -> GetPlaceResponse.builder()
                .id(place.getId())
                .description(place.getDescription())
                .positionX(place.getPositionX())
                .positionY(place.getPositionY())
                .positionZ(place.getPositionZ())
                .build();
    }

}
