package pl.piotr.iotdbmanagement.sensorfailure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.util.List;
import java.util.Optional;

public interface SensorFailureRepository extends JpaRepository<SensorCurrentFailure, Long> {

    Optional<SensorCurrentFailure> findFirstBySensor(Sensor sensor);

    List<SensorCurrentFailure> findAllByActivityVerificationTrue();

    @Modifying
    @Query("DELETE FROM SensorCurrentFailure f WHERE f.sensor.id=:sensor_id")
    void deleteBySensorId(@Param("sensor_id") Long id);

}
