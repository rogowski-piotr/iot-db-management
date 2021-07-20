package pl.piotr.iotdbmanagement.jobs.dto;

import lombok.*;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.utils.TriFunction;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MeasurementSoilMoistureResponse {

    private String sensor;

    private Boolean active;

    private Integer soilMoisture;

    public static TriFunction<MeasurementSoilMoistureResponse, Sensor, LocalDateTime, Measurement> dtoToEntitySoilMoistureMapper() {
        return (responseObj, sensor, dateTime) -> Measurement.builder()
                .value(Float.valueOf(responseObj.getSoilMoisture()))
                .measurementType(sensor.getMeasurementType())
                .date(dateTime)
                .sensor(sensor)
                .place(sensor.getActualPosition())
                .build();
    }

}
