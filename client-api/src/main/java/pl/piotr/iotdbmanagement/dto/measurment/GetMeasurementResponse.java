package pl.piotr.iotdbmanagement.dto.measurment;

import lombok.*;
import pl.piotr.iotdbmanagement.measurement.Measurement;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMeasurementResponse {

    private String id;

    private Float value;

    private String measurementType;

    private LocalDateTime date;

    private Place place;

    private Sensor sensor;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    private static class Place {
        String id;

        String description;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    private static class Sensor {
        String id;

        String socket;
    }

    public static Function<Measurement, GetMeasurementResponse> entityToDtoMapper() {
        return measurement -> GetMeasurementResponse.builder()
                .id(measurement.getId())
                .value(measurement.getValue())
                .measurementType(measurement.getMeasurementType())
                .date(measurement.getDate())
                .place(
                        measurement.getPlace() != null
                            ? Place.builder()
                                .id(measurement.getPlace().getId())
                                .description(measurement.getPlace().getDescription())
                                .build()
                            : null
                )
                .sensor(
                        measurement.getSensor() != null
                        ? Sensor.builder()
                            .id(measurement.getSensor().getId())
                            .socket(measurement.getSensor().getSocket())
                            .build()
                        : null
                )
                .build();
    }

}
