package com.iotdbmanagement.core.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iotdbmanagement.core.enums.MeasurementsFrequency;
import com.iotdbmanagement.core.measurementtype.MeasurementType;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findAllByMeasurementsFrequencyAndIsActiveIsFalse(MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementsFrequencyAndIsActiveIsTrue(MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementTypeAndMeasurementsFrequency(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency);

    List<Sensor> findAllByMeasurementTypeOrMeasurementsFrequency(MeasurementType measurementType, MeasurementsFrequency measurementsFrequency);

    Optional<Sensor> findBySocket(String socket);

}
