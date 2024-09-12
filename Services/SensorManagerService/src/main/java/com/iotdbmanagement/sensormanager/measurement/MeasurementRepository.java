package com.iotdbmanagement.sensormanager.measurement;

import com.iotdbmanagement.sensormanager.measurementtype.MeasurementType;
import com.iotdbmanagement.sensormanager.place.Place;
import com.iotdbmanagement.sensormanager.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findAllByPlace(Place place);

    List<Measurement> findAllBySensor(Sensor sensor);

    List<Measurement> findAllBySensorAndPlace(Sensor sensor, Place place);

    List<Measurement> findAllByMeasurementType(MeasurementType measurementType);

    void deleteAllByPlaceIsNullAndSensorIsNull();
}