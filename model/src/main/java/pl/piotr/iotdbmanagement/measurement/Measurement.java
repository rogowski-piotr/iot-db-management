package pl.piotr.iotdbmanagement.measurement;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
@Document(collection = "measurements")
public class Measurement implements Serializable {

    @Id
    private String id;

    @NotNull
    private Float value;

    private LocalDateTime date;

    @NotBlank
    private String measurementType;

    @DBRef
    private Sensor sensor;

    @DBRef
    private Place place;

}
