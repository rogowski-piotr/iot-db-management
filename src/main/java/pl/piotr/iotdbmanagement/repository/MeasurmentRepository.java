package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;

import java.util.List;
import java.util.UUID;

public interface MeasurmentRepository extends JpaRepository<Measurment, UUID> {

    List<Measurment> findAllByPlace(Place place);

    List<Measurment> findAllByMeasurmentDate(MeasurmentDate measurmentDate);

    List<Measurment> findAllBySensor(Sensor sensor);

}
