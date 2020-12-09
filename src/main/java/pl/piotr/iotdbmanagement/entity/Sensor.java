package pl.piotr.iotdbmanagement.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
@Table(name = "sensors")
public class Sensor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "measurment_type")
    private String measurmentType;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "socket")
    private String socket;

    @OneToOne
    @JoinColumn(name = "last_measurment", referencedColumnName = "id")
    private MeasurmentDate lastMeasurment;

    @OneToOne
    @JoinColumn(name = "actual_position", referencedColumnName = "id")
    private Place actualPosition;

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
