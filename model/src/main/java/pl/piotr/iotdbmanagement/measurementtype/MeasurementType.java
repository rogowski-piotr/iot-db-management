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
@Table(name = "measurment_types")
public class MeasurementType implements Serializable {

    @Id
    @SequenceGenerator(name = "measurement_type_id_generator", sequenceName = "measurement_type_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_type_id_generator")
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