package pl.piotr.iotdbmanagement.dto.stats;

import lombok.*;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetAllStatsResponse {

    private Integer successes;

    private Integer failures;

    public static BiFunction<Integer, Integer, GetAllStatsResponse> entityToDtoMapper() {
        return (success, failures) -> GetAllStatsResponse.builder()
                .successes(success)
                .failures(failures)
                .build();
    }

}
