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

    private String name;

    private String socket;

    private Boolean isActive;

    private String measurementType;

    private String measurementsFrequency;

    private LocalDateTime lastMeasurment;

    private SensorSettings sensorSettings;

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

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    private static class SensorSettings {
        private Long id;

        private String name;

        private Integer acceptableConsecutiveFailures;

        private Integer cyclesToRefresh;

        private Integer requestTimeout;
    }

    public static Function<Sensor, GetSensorResponse> entityToDtoMapper() {
        return sensor -> GetSensorResponse.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .socket(sensor.getSocket())
                .isActive(sensor.getIsActive())
                .measurementType(sensor.getMeasurementType().getType())
                .measurementsFrequency(sensor.getMeasurementsFrequency().name())
                .lastMeasurment(sensor.getLastMeasurment())
                .sensorSettings(
                        SensorSettings.builder()
                                .id(sensor.getSensorSettings().getId())
                                .name(sensor.getSensorSettings().getName())
                                .acceptableConsecutiveFailures(sensor.getSensorSettings().getAcceptableConsecutiveFailures())
                                .cyclesToRefresh(sensor.getSensorSettings().getCyclesToRefresh())
                                .requestTimeout(sensor.getSensorSettings().getRequestTimeout())
                                .build()
                )
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