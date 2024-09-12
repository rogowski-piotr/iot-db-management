package com.iotdbmanagement.sensormanager.jobs.dto;

import com.iotdbmanagement.common.utils.TriFunction;
import com.iotdbmanagement.sensormanager.measurement.Measurement;
import com.iotdbmanagement.sensormanager.sensor.Sensor;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SoilMoistureResponse {

    private String sensor;

    private Boolean active;

    private Integer soilMoisture;

    public static TriFunction<SoilMoistureResponse, Sensor, LocalDateTime, Measurement> dtoToEntitySoilMoistureMapper() {
        return (responseObj, sensor, dateTime) -> Measurement.builder()
                .value(Float.valueOf(responseObj.getSoilMoisture()))
                .measurementType(sensor.getMeasurementType())
                .date(dateTime)
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();
    }

}
