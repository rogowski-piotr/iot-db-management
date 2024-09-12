package com.iotdbmanagement.sensormanager.sensor;

import com.iotdbmanagement.sensormanager.sensorfailure.SensorCurrentFailure;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.iotdbmanagement.sensormanager.connectionstats.ConnectionStats;
import com.iotdbmanagement.sensormanager.measurementtype.MeasurementType;
import com.iotdbmanagement.sensormanager.place.Place;
import com.iotdbmanagement.sensormanager.enums.MeasurementsFrequency;
import com.iotdbmanagement.sensormanager.measurement.Measurement;
import com.iotdbmanagement.sensormanager.sensorsettings.SensorSettings;

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
    @SequenceGenerator(name = "sensor_id_generator", sequenceName = "sensor_id_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_id_generator")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "socket")
    private String socket;

    @Column(name = "active")
    private Boolean isActive;

    @Column(name = "measurement_frequency")
    @Enumerated(EnumType.STRING)
    private MeasurementsFrequency measurementsFrequency;

    @Column(name = "last_measurment")
    private LocalDateTime lastMeasurment;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "measurement_type_id")
    private MeasurementType measurementType;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="place_id")
    private Place actualPosition;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="sensor_settings_id")
    private SensorSettings sensorSettings;

    @ToString.Exclude
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    @ToString.Exclude
    @OneToOne(mappedBy = "sensor", cascade = CascadeType.ALL)
    private SensorCurrentFailure failure;

    @ToString.Exclude
    @OneToMany(mappedBy = "sensor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ConnectionStats> connectionStats;

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