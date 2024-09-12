package com.iotdbmanagement.core.dto.sensor;

import com.iotdbmanagement.common.utils.QuadriFunction;
import com.iotdbmanagement.core.enums.MeasurementsFrequency;
import com.iotdbmanagement.core.measurementtype.MeasurementType;
import com.iotdbmanagement.core.place.Place;
import com.iotdbmanagement.core.sensor.Sensor;
import com.iotdbmanagement.core.sensorsettings.SensorSettings;
import lombok.*;

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
