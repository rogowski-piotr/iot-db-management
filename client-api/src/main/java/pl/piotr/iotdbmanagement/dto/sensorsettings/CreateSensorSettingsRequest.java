package pl.piotr.iotdbmanagement.dto.sensorsettings;

import lombok.*;
import pl.piotr.iotdbmanagement.sensorsettings.SensorSettings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateSensorSettingsRequest {

    @NotBlank(message = "can not be empty")
    @NotEmpty(message = "can not be empty")
    private String name;

    @NotNull(message = "can not be null")
    private Integer acceptableConsecutiveFailures;

    @NotNull(message = "can not be null")
    private Integer cyclesToRefresh;

    @NotNull(message = "can not be null")
    private Integer requestTimeout;

    public static Function<CreateSensorSettingsRequest, SensorSettings> dtoToEntityMapper() {
        return request ->
                SensorSettings.builder()
                        .name(request.getName())
                        .acceptableConsecutiveFailures(request.getAcceptableConsecutiveFailures())
                        .cyclesToRefresh(request.getCyclesToRefresh())
                        .requestTimeout(request.getRequestTimeout())
                        .build();
    }

}
