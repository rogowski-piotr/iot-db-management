package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.entity.Measurement;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    List<Measurement> findAllByPlace(Place place);

    List<Measurement> findAllByDate(LocalDateTime dateTime);

    List<Measurement> findAllBySensor(Sensor sensor);

    List<Measurement> findAllBySensor_MeasurementType(MeasurementType measurementType);

}
