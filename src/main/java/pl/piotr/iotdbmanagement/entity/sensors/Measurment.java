package pl.piotr.iotdbmanagement.entity.sensors;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;

import javax.persistence.*;
import java.io.Serializable;
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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Measurment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "value", updatable = false)
    private Float value;

    @OneToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @OneToOne
    @JoinColumn(name = "date_id", referencedColumnName = "id")
    private MeasurmentDate measurmentDate;

}
