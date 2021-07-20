package pl.piotr.iotdbmanagement.jobs.dto;

import lombok.*;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.utils.QuadriFunction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MeasurmentTemperatureAndHumidityResponse {

    private String sensor;

    private Boolean active;

    private Float temperature;

    private Float humidity;

    public static QuadriFunction<MeasurmentTemperatureAndHumidityResponse, Sensor, MeasurementType, LocalDateTime, Measurement> dtoToEntityTemperatureMapper() {
        return (responseObj, sensor, measurementType, dateTime) -> Measurement.builder()
                .value(responseObj.getTemperature())
                .measurementType(measurementType)
                .date(dateTime)
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();
    }

    public static QuadriFunction<MeasurmentTemperatureAndHumidityResponse, Sensor, MeasurementType, LocalDateTime, Measurement> dtoToEntityHumidityMapper() {
        return (responseObj, sensor, measurementType, dateTime) -> Measurement.builder()
                .value(responseObj.getHumidity())
                .measurementType(measurementType)
                .date(dateTime)
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();
    }

}
