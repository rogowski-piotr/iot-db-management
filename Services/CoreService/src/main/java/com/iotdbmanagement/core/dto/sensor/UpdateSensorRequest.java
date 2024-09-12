package com.iotdbmanagement.core.dto.sensor;

import com.iotdbmanagement.common.utils.PentaFunction;
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
