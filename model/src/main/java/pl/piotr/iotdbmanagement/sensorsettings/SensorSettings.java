package pl.piotr.iotdbmanagement.sensorsettings;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.sensor.Sensor;

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
@Table(name = "sensor_settings")
public class SensorSettings implements Serializable {

    @Id
    @SequenceGenerator(name = "sensor_settings_id_generator", sequenceName = "sensor_settings_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_settings_id_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "acceptable_consecutive_failures")
    private Integer acceptableConsecutiveFailures;

    @Column(name = "cycles_to_refresh_activity")
    private Integer cyclesToRefresh;

    @Column(name = "request_timeout")
    private Integer requestTimeout;

    @ToString.Exclude
    @OneToMany(mappedBy = "sensorSettings", fetch = FetchType.LAZY)
    private List<Sensor> sensors;

}
