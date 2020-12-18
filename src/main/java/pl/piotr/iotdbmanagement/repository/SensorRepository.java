package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findAllByMeasurementsFrequency(MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByActualPosition(Place actualPosition);

    List<Sensor> findAllByLastMeasurment(MeasurmentDate lastMeasurment);

}
