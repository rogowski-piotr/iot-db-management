package pl.piotr.iotdbmanagement.measurementtype;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.measurement.Measurement;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "measurments_type")
public class MeasurementType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @Column(name = "type", updatable = false, nullable = false, unique = true)
    String type;

    @ToString.Exclude
    @OneToMany(mappedBy = "measurementType", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    @ToString.Exclude
    @OneToMany(mappedBy = "measurementType", fetch = FetchType.LAZY)
    private List<Sensor> sensors;

}