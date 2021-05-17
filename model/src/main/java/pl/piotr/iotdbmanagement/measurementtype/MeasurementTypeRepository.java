package pl.piotr.iotdbmanagement.measurementtype;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasurementTypeRepository extends JpaRepository<MeasurementType, String> {

    Optional<MeasurementType> findByType(String type);

}
