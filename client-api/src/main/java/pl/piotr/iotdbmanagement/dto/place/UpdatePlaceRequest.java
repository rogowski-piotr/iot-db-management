package pl.piotr.iotdbmanagement.dto.place;

import lombok.*;

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

    public static BiFunction<pl.piotr.iotdbmanagement.entity.Place, UpdatePlaceRequest, pl.piotr.iotdbmanagement.entity.Place> dtoToEntityUpdater() {
        return (place, request) -> {
            place.setDescription(request.getDescription());
            place.setPositionX(request.getPositionX());
            place.setPositionY(request.getPositionY());
            place.setPositionZ(request.getPositionZ());
            return place;
        };
    }

}
