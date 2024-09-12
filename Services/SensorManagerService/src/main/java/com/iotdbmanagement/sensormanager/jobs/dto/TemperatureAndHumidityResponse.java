package com.iotdbmanagement.sensormanager.jobs.dto;

import com.iotdbmanagement.common.utils.QuadriFunction;
import com.iotdbmanagement.sensormanager.measurement.Measurement;
import com.iotdbmanagement.sensormanager.measurementtype.MeasurementType;
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
public class TemperatureAndHumidityResponse {

    private String sensor;

    private Boolean active;

    private Float temperature;

    private Float humidity;

    public static QuadriFunction<TemperatureAndHumidityResponse, Sensor, MeasurementType, LocalDateTime, Measurement> dtoToEntityTemperatureMapper() {
        return (responseObj, sensor, measurementType, dateTime) -> Measurement.builder()
                .value(responseObj.getTemperature())
                .measurementType(measurementType)
                .date(dateTime)
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();
    }

    public static QuadriFunction<TemperatureAndHumidityResponse, Sensor, MeasurementType, LocalDateTime, Measurement> dtoToEntityHumidityMapper() {
        return (responseObj, sensor, measurementType, dateTime) -> Measurement.builder()
                .value(responseObj.getHumidity())
                .measurementType(measurementType)
                .date(dateTime)
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();
    }

}
