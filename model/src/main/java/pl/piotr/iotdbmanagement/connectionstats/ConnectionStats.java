package pl.piotr.iotdbmanagement.connectionstats;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "connection_stats")
public class ConnectionStats {

    @Id
    @SequenceGenerator(name = "connection_stats_id_generator", sequenceName = "connection_stats_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "connection_stats_id_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "successful_connections")
    private Integer successfulConnections;

    @Column(name = "failure_connections")
    private Integer failureConnections;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

}
