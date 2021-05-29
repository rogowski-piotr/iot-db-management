package pl.piotr.iotdbmanagement.sensor;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.place.Place;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
@Document(collection = "sensors")
public class Sensor implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String socket;

    @NotNull
    private Boolean isActive;

    @NotBlank
    private String measurementType;

    @NotNull
    private MeasurementsFrequency measurementsFrequency;

    private LocalDateTime lastMeasurment;

    @DBRef
    private Place actualPosition;

    @DBRef
    private List<Measurement> measurements;

    /*@PreRemove
    private void preRemove() {
        measurements.forEach(measurement -> measurement.setSensor(null));
    }*/

    public String getAddress() {
        StringBuilder address = new StringBuilder();
        for (char ch : this.socket.toCharArray()) {
            if (ch != ':') {
                address.append(ch);
            } else {
                break;
            }
        }
        return address.toString();
    }

    public Integer getPort() {
        StringBuilder port = new StringBuilder();
        boolean isPort = false;
        for (char ch : this.socket.toCharArray()) {
            if (isPort) port.append(ch);
            if (ch == ':') isPort = true;
        }
        return Integer.valueOf(port.toString());
    }

}
