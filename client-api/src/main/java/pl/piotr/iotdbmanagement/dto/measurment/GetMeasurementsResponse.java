package pl.piotr.iotdbmanagement.dto.measurment;

import lombok.*;
import pl.piotr.iotdbmanagement.enums.MeasurementType;

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

        private MeasurementType measurementType;

        private LocalDateTime date;

    }

    public static Function<Collection<pl.piotr.iotdbmanagement.measurement.Measurement>, Iterable<GetMeasurementsResponse.Measurement>> entityToDtoMapper() {
        return measurements -> {
            return measurements.stream()
                    .map(measurement -> Measurement.builder()
                            .id(measurement.getId())
                            .value(measurement.getValue())
                            .measurementType(measurement.getMeasurementType())
                            .date(measurement.getDate())
                            .build())
                    .collect(Collectors.toList());
        };
    }

}
