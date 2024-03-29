package pl.piotr.iotdbmanagement.dto.sensorsettings;

import lombok.*;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;
import pl.piotr.iotdbmanagement.utils.TriFunction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateSensorSettingsRequest {

    @NotBlank(message = "can not be empty")
    @NotEmpty(message = "can not be empty")
    private String name;

    @NotNull(message = "can not be null")
    private Integer acceptableConsecutiveFailures;

    @NotNull(message = "can not be null")
    private Integer cyclesToRefresh;

    @NotNull(message = "can not be null")
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
