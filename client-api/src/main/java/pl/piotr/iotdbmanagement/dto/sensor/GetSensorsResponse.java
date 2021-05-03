package pl.piotr.iotdbmanagement.dto.sensor;

import lombok.*;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GetSensorsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Sensor {
        private Long id;

        private String socket;

        private Boolean isActive;
    }

    public static Function<Collection<pl.piotr.iotdbmanagement.entity.Sensor>, Iterable<Sensor>> entityToDtoMapper() {
        return sensors -> {
            return sensors.stream()
                    .map(sensor -> Sensor.builder()
                            .id(sensor.getId())
                            .socket(sensor.getSocket())
                            .isActive(sensor.getIsActive())
                            .build())
                    .collect(Collectors.toList());
        };
    }

}