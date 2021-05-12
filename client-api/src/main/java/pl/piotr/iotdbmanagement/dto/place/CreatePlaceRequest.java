package pl.piotr.iotdbmanagement.dto.place;

import lombok.*;
import pl.piotr.iotdbmanagement.entity.Place;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreatePlaceRequest {

    private String description;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    public static Function<CreatePlaceRequest, pl.piotr.iotdbmanagement.entity.Place> dtoToEntityMapper() {
        return request ->
                Place.builder()
                        .description(request.getDescription())
                        .positionX(request.getPositionX())
                        .positionY(request.getPositionY())
                        .positionZ(request.getPositionZ())
                        .build();
    }

}
