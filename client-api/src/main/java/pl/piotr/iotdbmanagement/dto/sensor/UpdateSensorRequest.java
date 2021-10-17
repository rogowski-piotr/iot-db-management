package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.utils.PentaFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateSensorRequest {

    private String socket;

    private String name;

    private Boolean isActive;

    private String measurementType;

    private MeasurementsFrequency measurementsFrequency;

    private Long actualPosition;

    private Long sensorSettingsId;

    public static PentaFunction<Sensor, UpdateSensorRequest, Place, MeasurementType, SensorSettings, Sensor> dtoToEntityUpdater() {
        return (sensor, request, place, measurementType, sensorSettings) -> {
            sensor.setSocket(request.getSocket());
            sensor.setName(request.getName());
            sensor.setIsActive(request.getIsActive());
            sensor.setMeasurementType(measurementType);
            sensor.setMeasurementsFrequency(request.getMeasurementsFrequency());
            sensor.setActualPosition(place);
            sensor.setSensorSettings(sensorSettings);
            return sensor;
        };
    }

}
