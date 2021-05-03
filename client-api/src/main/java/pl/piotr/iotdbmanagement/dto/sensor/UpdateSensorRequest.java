package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.utils.TriFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateSensorRequest {

    private String socket;

    private Boolean isActive;

    private MeasurementType measurementType;

    private MeasurementsFrequency measurementsFrequency;

    private Long actualPosition;

    public static TriFunction<Sensor, UpdateSensorRequest, Place, Sensor> dtoToEntityUpdater() {
        return (sensor, request, place) -> {
            sensor.setSocket(request.getSocket());
            sensor.setIsActive(request.getIsActive());
            sensor.setMeasurementType(request.getMeasurementType());
            sensor.setMeasurementsFrequency(request.getMeasurementsFrequency());
            sensor.setActualPosition(place);
            return sensor;
        };
    }

}
