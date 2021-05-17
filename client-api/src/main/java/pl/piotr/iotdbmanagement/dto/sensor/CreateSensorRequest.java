package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.utils.TriFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateSensorRequest {

    private String socket;

    private String measurementType;

    private MeasurementsFrequency measurementsFrequency;

    private Long actualPositionPlaceId;

    public static TriFunction<CreateSensorRequest, Place, MeasurementType, Sensor> dtoToEntityMapper() {
        return (request, place, type) ->
                Sensor.builder()
                    .socket(request.getSocket())
                    .measurementType(type)
                    .isActive(true)
                    .measurementsFrequency(request.getMeasurementsFrequency())
                    .actualPosition(place)
                    .build();
    }

}
