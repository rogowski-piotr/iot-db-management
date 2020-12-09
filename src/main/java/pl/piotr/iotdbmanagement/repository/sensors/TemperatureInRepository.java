package pl.piotr.iotdbmanagement.repository.sensors;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piotr.iotdbmanagement.entity.sensors.TemperatureIn;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TemperatureInRepository extends JpaRepository<TemperatureIn, UUID> {
}
