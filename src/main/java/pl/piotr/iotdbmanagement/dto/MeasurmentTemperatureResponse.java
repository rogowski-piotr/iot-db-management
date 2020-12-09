package pl.piotr.iotdbmanagement.dto;

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
public class MeasurmentTemperatureResponse {

    private String sensor;

    private Boolean isWorking;

    private Float temperature;

    private Float humidity;

    public static Function<MeasurmentTemperatureResponse, Measurment> dtoToEntityMapper() {
        return measurment -> Measurment.builder()
                .value(measurment.getTemperature())
                .build();
    }

}
