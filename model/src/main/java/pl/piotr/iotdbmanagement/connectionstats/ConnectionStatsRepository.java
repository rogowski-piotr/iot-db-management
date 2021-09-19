package pl.piotr.iotdbmanagement.connectionstats;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConnectionStatsRepository extends JpaRepository<ConnectionStats, Long> {

    Optional<ConnectionStats> findBySensorAndDate(Sensor sensor, LocalDate date);

    List<ConnectionStats> findAllByDate(LocalDate date);

}