package pl.piotr.iotdbmanagement.connectionstats;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.util.Optional;

public interface ConnectionStatsRepository extends JpaRepository<ConnectionStats, Long> {

    Optional<ConnectionStats> findBySensor(Sensor sensor);

}