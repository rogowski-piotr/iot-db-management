package pl.piotr.iotdbmanagement.sensor;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.measurement.Measurement;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "sensors")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "socket")
    private String socket;

    @Column(name = "active")
    private Boolean isActive;

    @Column(name = "measurment_type")
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    @Column(name = "measurement_frequency")
    @Enumerated(EnumType.STRING)
    private MeasurementsFrequency measurementsFrequency;

    @Column(name = "last_measurment")
    private LocalDateTime lastMeasurment;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="actual_position")
    private Place actualPosition;

    @ToString.Exclude
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    @PreRemove
    private void preRemove() {
        measurements.forEach(measurement -> measurement.setSensor(null));
    }

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