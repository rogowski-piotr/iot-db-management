package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Sensor;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeasurmentDateRepository extends JpaRepository<MeasurmentDate, UUID> {

    List<MeasurmentDate> findAllBySensor(Sensor sensor);

}
