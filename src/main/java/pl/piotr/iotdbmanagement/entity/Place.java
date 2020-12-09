package pl.piotr.iotdbmanagement.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.piotr.iotdbmanagement.entity.sensors.Measurment;

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
@Table(name = "places")
public class Place implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "description", length = 25)
    private String description;

    @Column(name = "position_x")
    private int positionX;

    @Column(name = "position_y")
    private int positionY;

    @Column(name = "position_z")
    private int positionZ;

    @OneToOne(mappedBy = "place")
    private Measurment measurment;

}
