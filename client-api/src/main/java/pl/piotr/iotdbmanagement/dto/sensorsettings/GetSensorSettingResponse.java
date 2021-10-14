package pl.piotr.iotdbmanagement.dto.sensorsettings;

import lombok.*;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetSensorSettingResponse {

    private Long id;

    private String name;

    private Integer acceptableConsecutiveFailures;

    private Integer cyclesToRefresh;

    private Integer requestTimeout;

    public static Function<SensorSettings, GetSensorSettingResponse> entityToDtoMapper() {
        return sensorSettings -> GetSensorSettingResponse.builder()
                .id(sensorSettings.getId())
                .name(sensorSettings.getName())
                .acceptableConsecutiveFailures(sensorSettings.getAcceptableConsecutiveFailures())
                .cyclesToRefresh(sensorSettings.getCyclesToRefresh())
                .requestTimeout(sensorSettings.getRequestTimeout())
                .build();
    }

}