package pl.piotr.iotdbmanagement.sensorfailure;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.util.List;
import java.util.Optional;

public interface SensorFailureRepository extends JpaRepository<SensorCurrentFailure, Long> {

    Optional<SensorCurrentFailure> findFirstBySensor(Sensor sensor);

    List<SensorCurrentFailure> findAllByActivityVerificationTrue();

}
