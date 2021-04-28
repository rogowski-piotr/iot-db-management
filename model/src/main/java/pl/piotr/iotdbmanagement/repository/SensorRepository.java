package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findAllByMeasurementsFrequency(MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementsFrequencyAndIsActive(MeasurementsFrequency measurementsFrequency, Boolean activeState);

    List<Sensor> findAllByMeasurementType(MeasurementType measurementType);

    List<Sensor> findAllByMeasurementTypeAndMeasurementsFrequency(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByActualPosition(Place actualPosition);

    List<Sensor> findAllByLastMeasurment(LocalDateTime dateTime);

}
