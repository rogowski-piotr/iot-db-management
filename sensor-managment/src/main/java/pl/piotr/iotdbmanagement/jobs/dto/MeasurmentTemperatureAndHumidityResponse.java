package pl.piotr.iotdbmanagement.jobs.dto;

import lombok.*;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.enums.MeasurementType;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
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

    public static BiFunction<MeasurmentTemperatureAndHumidityResponse, Measurment, Measurment> dtoToEntityTemperatureMapper() {
        return (responseObj, infoObj) -> Measurment.builder()
                .value(responseObj.getTemperature())
                .measurementType(MeasurementType.TEMPERATURE)
                .date(infoObj.getDate())
                .sensor(infoObj.getSensor())
                .place(infoObj.getPlace())
                .build();
    }

    public static BiFunction<MeasurmentTemperatureAndHumidityResponse, Measurment, Measurment> dtoToEntityHumidityMapper() {
        return (responseObj, infoObj) -> Measurment.builder()
                .value(responseObj.getHumidity())
                .measurementType(MeasurementType.HUMIDITY)
                .date(infoObj.getDate())
                .sensor(infoObj.getSensor())
                .place(infoObj.getPlace())
                .build();
    }

}
