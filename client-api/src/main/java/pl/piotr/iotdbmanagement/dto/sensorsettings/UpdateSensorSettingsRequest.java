package pl.piotr.iotdbmanagement.dto.sensorsettings;

import lombok.*;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.utils.TriFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateSensorSettingsRequest {

    private String name;

    private Integer acceptableConsecutiveFailures;

    private Integer cyclesToRefresh;

    private Integer requestTimeout;

    public static TriFunction<SensorSettings, UpdateSensorSettingsRequest, Long, SensorSettings> dtoToEntityUpdater() {
        return (sensorSettings, request, id) -> {
            sensorSettings.setId(id);
            sensorSettings.setName(request.getName());
            sensorSettings.setAcceptableConsecutiveFailures(request.getAcceptableConsecutiveFailures());
            sensorSettings.setCyclesToRefresh(request.getCyclesToRefresh());
            sensorSettings.setRequestTimeout(request.getRequestTimeout());
            return sensorSettings;
        };
    }

}
