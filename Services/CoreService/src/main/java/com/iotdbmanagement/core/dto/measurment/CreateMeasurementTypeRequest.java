package com.iotdbmanagement.core.dto.measurment;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateMeasurementTypeRequest {

    private String type;

}