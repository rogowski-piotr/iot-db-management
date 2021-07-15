package pl.piotr.iotdbmanagement.place;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.sensor.Sensor;
import pl.piotr.iotdbmanagement.measurement.Measurement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "places")
public class Place implements Serializable {

    @Id
    @SequenceGenerator(name = "place_id_generator", sequenceName = "place_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_id_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "description", length = 25, nullable = false, unique = true)
    private String description;

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;

    @Column(name = "position_z")
    private Integer positionZ;

    @ToString.Exclude
    @OneToMany(mappedBy = "place", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    @ToString.Exclude
    @OneToMany(mappedBy = "actualPosition", fetch = FetchType.LAZY)
    private List<Sensor> sensors;

    @PreRemove
    private void preRemove() {
        sensors.forEach(sensor -> sensor.setActualPosition(null));
        measurements.forEach(measurement -> measurement.setPlace(null));
    }

}