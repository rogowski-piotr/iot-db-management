package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MeasurmentRepository extends JpaRepository<Measurment, UUID> {

    List<Measurment> findAllByPlace(Place place);

    List<Measurment> findAllByDate(LocalDateTime dateTime);

    List<Measurment> findAllBySensor(Sensor sensor);

    List<Measurment> findAllBySensor_MeasurementType(MeasurementType measurementType);

}
