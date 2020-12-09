package pl.piotr.iotdbmanagement.entity.sensors;

import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;


@SuperBuilder
@Entity
@Table(name = "temperature_outside_mesurments")
public class TemperatureOut extends Measurment {
}
