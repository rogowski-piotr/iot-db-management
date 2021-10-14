package pl.piotr.iotdbmanagement.dto.sensorsettings;

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
public class GetSensorSettingsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class SensorSettings {
        private Long id;

        private String name;

        private Integer acceptableConsecutiveFailures;

        private Integer cyclesToRefresh;

        private Integer requestTimeout;
    }

    public static Function<Collection<pl.piotr.iotdbmanagement.sensorsettings.SensorSettings>, Iterable<GetSensorSettingsResponse.SensorSettings>> entityToDtoMapper() {
        return sensorSettings -> {
            return sensorSettings.stream()
                    .map(sensorSetting -> GetSensorSettingsResponse.SensorSettings.builder()
                            .id(sensorSetting.getId())
                            .name(sensorSetting.getName())
                            .acceptableConsecutiveFailures(sensorSetting.getAcceptableConsecutiveFailures())
                            .cyclesToRefresh(sensorSetting.getCyclesToRefresh())
                            .requestTimeout(sensorSetting.getRequestTimeout())
                            .build())
                    .collect(Collectors.toList());
        };
    }

}
