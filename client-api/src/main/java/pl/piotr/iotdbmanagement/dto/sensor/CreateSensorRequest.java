package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.utils.QuadriFunction;
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

    private String name;

    private String measurementType;

    private MeasurementsFrequency measurementsFrequency;

    private Long actualPositionPlaceId;

    private Long sensorSettingsId;

    public static QuadriFunction<CreateSensorRequest, Place, MeasurementType, SensorSettings, Sensor> dtoToEntityMapper() {
        return (request, place, type, sensorSettings) ->
                Sensor.builder()
                    .socket(request.getSocket())
                    .name(request.getName())
                    .measurementType(type)
                    .isActive(true)
                    .measurementsFrequency(request.getMeasurementsFrequency())
                    .actualPosition(place)
                    .sensorSettings(sensorSettings)
                    .build();
    }

}
