package pl.piotr.iotdbmanagement.dto.place;

import lombok.*;
import pl.piotr.iotdbmanagement.place.Place;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdatePlaceRequest {

    private String description;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    public static BiFunction<Place, UpdatePlaceRequest, Place> dtoToEntityUpdater() {
        return (place, request) -> {
            place.setDescription(request.getDescription());
            place.setPositionX(request.getPositionX());
            place.setPositionY(request.getPositionY());
            place.setPositionZ(request.getPositionZ());
            return place;
        };
    }

}
