package pl.piotr.iotdbmanagement.repository.sensors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotr.iotdbmanagement.entity.sensors.TemperatureOut;

import java.util.UUID;

@Repository
public interface TemperatureOutRepository extends JpaRepository<TemperatureOut, UUID> {
}
