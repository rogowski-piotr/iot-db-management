package pl.piotr.iotdbmanagement.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.measurementtype.MeasurementType;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findAllByPlace(Place place);

    List<Measurement> findAllBySensor(Sensor sensor);

    List<Measurement> findAllBySensorAndPlace(Sensor sensor, Place place);

    List<Measurement> findAllByMeasurementType(MeasurementType measurementType);

    void deleteAllByPlaceIsNullAndSensorIsNull();
}