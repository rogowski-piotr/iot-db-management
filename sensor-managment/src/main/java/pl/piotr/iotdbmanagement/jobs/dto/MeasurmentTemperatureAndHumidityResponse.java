package pl.piotr.iotdbmanagement.jobs.dto;

import lombok.*;
import pl.piotr.iotdbmanagement.measurement.Measurement;

import java.util.function.BiFunction;

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

    public static BiFunction<MeasurmentTemperatureAndHumidityResponse, Measurement, Measurement> dtoToEntityTemperatureMapper() {
        return (responseObj, infoObj) -> Measurement.builder()
                .value(responseObj.getTemperature())
                .measurementType(infoObj.getMeasurementType())
                .date(infoObj.getDate())
                .sensor(infoObj.getSensor())
                .place(infoObj.getPlace())
                .build();
    }

    public static BiFunction<MeasurmentTemperatureAndHumidityResponse, Measurement, Measurement> dtoToEntityHumidityMapper() {
        return (responseObj, infoObj) -> Measurement.builder()
                .value(responseObj.getHumidity())
                .measurementType(infoObj.getMeasurementType())
                .date(infoObj.getDate())
                .sensor(infoObj.getSensor())
                .place(infoObj.getPlace())
                .build();
    }

}
