package com.iotdbmanagement.core.connectionstats;

import com.iotdbmanagement.core.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConnectionStatsRepository extends JpaRepository<ConnectionStats, Long> {

    Optional<ConnectionStats> findBySensorAndDate(Sensor sensor, LocalDate date);

    List<ConnectionStats> findAllByDate(LocalDate date);

}