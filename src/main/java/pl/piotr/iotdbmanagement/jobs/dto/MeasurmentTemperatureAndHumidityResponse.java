package pl.piotr.iotdbmanagement.jobs.dto;

import lombok.*;
import pl.piotr.iotdbmanagement.entity.Measurment;

import java.util.function.Function;

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

    public static Function<MeasurmentTemperatureAndHumidityResponse, Measurment> dtoToEntityTemperatureMapper() {
        return measurment -> Measurment.builder()
                .value(measurment.getTemperature())
                .build();
    }

    public static Function<MeasurmentTemperatureAndHumidityResponse, Measurment> dtoToEntityHumidityMapper() {
        return measurment -> Measurment.builder()
                .value(measurment.getHumidity())
                .build();
    }

}
