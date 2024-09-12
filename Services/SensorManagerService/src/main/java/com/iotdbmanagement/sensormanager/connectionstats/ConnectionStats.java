package com.iotdbmanagement.sensormanager.connectionstats;

import com.iotdbmanagement.sensormanager.sensor.Sensor;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "date")
    private LocalDate date;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

}
