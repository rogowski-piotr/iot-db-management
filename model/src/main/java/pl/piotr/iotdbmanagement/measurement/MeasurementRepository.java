package pl.piotr.iotdbmanagement.measurement;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.piotr.iotdbmanagement.place.Place;
import pl.piotr.iotdbmanagement.sensor.Sensor;

import java.util.List;

public interface MeasurementRepository extends MongoRepository<Measurement, String> {

    List<Measurement> findAllByPlace(Place place);

    List<Measurement> findAllBySensor(Sensor sensor);

    List<Measurement> findAllBySensorAndPlace(Sensor sensor, Place place);

    List<Measurement> findAllByMeasurementType(String measurementType);

    void deleteAllByPlaceIsNullAndSensorIsNull();

}
