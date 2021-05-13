package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateSensorRequest {

    private String socket;

    private MeasurementType measurementType;

    private MeasurementsFrequency measurementsFrequency;

    private Long actualPositionPlaceId;

    public static BiFunction<CreateSensorRequest, Place, Sensor> dtoToEntityMapper() {
        return (request, place) ->
                Sensor.builder()
                    .socket(request.getSocket())
                    .measurementType(request.getMeasurementType())
                    .isActive(true)
                    .measurementsFrequency(request.getMeasurementsFrequency())
                    .actualPosition(place)
                    .build();
    }

}
