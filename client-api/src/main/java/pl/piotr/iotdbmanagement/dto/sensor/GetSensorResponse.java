package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetSensorResponse {
    private Long id;

    private String socket;

    private Boolean isActive;

    private String measurementType;

    private String measurementsFrequency;

    private LocalDateTime lastMeasurment;

    private Place actualPosition;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    private static class Place {
        Long id;

        String description;
    }

    public static Function<Sensor, GetSensorResponse> entityToDtoMapper() {
        return sensor -> GetSensorResponse.builder()
                .id(sensor.getId())
                .socket(sensor.getSocket())
                .isActive(sensor.getIsActive())
                .measurementType(sensor.getMeasurementType().name())
                .measurementsFrequency(sensor.getMeasurementsFrequency().name())
                .lastMeasurment(sensor.getLastMeasurment())
                .actualPosition(
                    sensor.getActualPosition() != null
                        ? Place.builder()
                            .id(sensor.getActualPosition().getId())
                            .description(sensor.getActualPosition().getDescription())
                            .build()
                        : null
                )
                .build();
    }

}