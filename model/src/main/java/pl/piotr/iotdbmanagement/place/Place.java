package pl.piotr.iotdbmanagement.place;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
@Document(collection = "places")
public class Place implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String description;

    private Integer positionX;

    private Integer positionY;

    private Integer positionZ;

    @DBRef
    private List<Sensor> sensors;

    @DBRef
    private List<Measurement> measurements;

    /*@PreRemove
    private void preRemove() {
        sensors.forEach(sensor -> sensor.setActualPosition(null));
        measurements.forEach(measurement -> measurement.setPlace(null));
    }*/

}