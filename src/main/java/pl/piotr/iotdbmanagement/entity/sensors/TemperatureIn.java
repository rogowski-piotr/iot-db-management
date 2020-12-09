package pl.piotr.iotdbmanagement.entity.sensors;

import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@SuperBuilder
@Entity
@Table(name = "temperature_inside_mesurments")
public class TemperatureIn extends Measurment {
}
