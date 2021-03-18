package pl.piotr.iotdbmanagement.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.enums.MeasurementType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Entity
@Table(name = "measurments")
public class Measurment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "value", updatable = false)
    private Float value;

    @Column(name = "measurment_type")
    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    @Column(name = "date")
    private LocalDateTime date;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

}