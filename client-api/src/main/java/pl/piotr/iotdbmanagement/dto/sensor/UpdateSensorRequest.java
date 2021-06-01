package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.utils.QuadriFunction;

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

    private String measurementType;

    private MeasurementsFrequency measurementsFrequency;

    private String actualPosition;

    public static QuadriFunction<Sensor, UpdateSensorRequest, Place, MeasurementType, Sensor> dtoToEntityUpdater() {
        return (sensor, request, place, measurementType) -> {
            sensor.setSocket(request.getSocket());
            sensor.setIsActive(request.getIsActive());
            sensor.setMeasurementType(measurementType.getType());
            sensor.setMeasurementsFrequency(request.getMeasurementsFrequency());
            sensor.setActualPosition(place);
            return sensor;
        };
    }

}
