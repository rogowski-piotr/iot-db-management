package pl.piotr.iotdbmanagement.sensorfailure;

import jdk.jfr.Unsigned;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "sensor_current_failures")
public class SensorCurrentFailure implements Serializable {

    public SensorCurrentFailure(Sensor sensor) {
        this.sensor = sensor;
        this.leftCyclesToRefresh = sensor.getSensorSettings().getCyclesToRefresh();
    }

    @Id
    @SequenceGenerator(name = "sensor_failures_id_generator", sequenceName = "sensor_failures_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_failures_id_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "consecutive_failures")
    @Unsigned
    private Integer consecutiveFailures = 0;

    @Column(name = "left_cycles_to_refresh")
    private Integer leftCyclesToRefresh;

    @Column(name = "activity_verification")
    private Boolean activityVerification = false;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    public void incrementConsecutiveFailures() {
        consecutiveFailures++;
    }

    public void decrementLeftCycleToRefresh() {
        leftCyclesToRefresh = leftCyclesToRefresh == 0 ? 0 : leftCyclesToRefresh - 1;
    }

}
