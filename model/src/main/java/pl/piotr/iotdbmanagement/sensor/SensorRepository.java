package pl.piotr.iotdbmanagement.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findAllByMeasurementsFrequencyAndIsActiveIsFalse(MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementsFrequencyAndIsActiveIsTrue(MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementTypeAndMeasurementsFrequency(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementTypeOrMeasurementsFrequency(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency);

    Optional<Sensor> findBySocket(String socket);

}
