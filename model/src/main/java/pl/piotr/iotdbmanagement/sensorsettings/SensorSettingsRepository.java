package pl.piotr.iotdbmanagement.sensorsettings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorSettingsRepository extends JpaRepository<SensorSettings, Long> {

    Optional<SensorSettings> findByName(String name);

}
