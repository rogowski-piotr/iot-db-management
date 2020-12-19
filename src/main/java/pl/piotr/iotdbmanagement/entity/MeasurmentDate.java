package pl.piotr.iotdbmanagement.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "dates")
public class MeasurmentDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ToString.Exclude
    @OneToOne(mappedBy = "measurmentDate")
    private Measurment measurment;

    @ToString.Exclude
    @OneToOne(mappedBy = "lastMeasurment")
    private Sensor sensor;

}
