package pl.piotr.iotdbmanagement.dto.measurment;

import lombok.*;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GetMeasurementsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Measurement {

        private UUID id;

        private Float value;

        private String measurementType;

        private LocalDateTime date;

        private String sensorName;

    }

    public static Function<Collection<pl.piotr.iotdbmanagement.measurement.Measurement>, Iterable<GetMeasurementsResponse.Measurement>> entityToDtoMapper() {
        return measurements -> {
            return measurements.stream()
                    .map(measurement -> Measurement.builder()
                            .id(measurement.getId())
                            .value(measurement.getValue())
                            .measurementType(measurement.getMeasurementType().getType())
                            .date(measurement.getDate())
                            .sensorName(measurement.getSensor().getName())
                            .build())
                    .collect(Collectors.toList());
        };
    }

}
