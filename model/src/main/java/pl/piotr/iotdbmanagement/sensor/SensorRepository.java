package pl.piotr.iotdbmanagement.sensor;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.piotr.iotdbmanagement.enums.MeasurementsFrequency;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends MongoRepository <Sensor, String> {

    List<Sensor> findAllByMeasurementsFrequencyAndIsActive(MeasurementsFrequency measurementsFrequency, Boolean activeState);

    List<Sensor> findAllByMeasurementTypeAndMeasurementsFrequency(String measurementType, MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementTypeOrMeasurementsFrequency(String measurementType, MeasurementsFrequency measurementsFrequency);

    Optional<Sensor> findBySocket(String socket);

}
