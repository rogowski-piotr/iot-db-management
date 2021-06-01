package pl.piotr.iotdbmanagement.measurementtype;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MeasurementTypeRepository extends MongoRepository<MeasurementType, String> {

    Optional<MeasurementType> findByType(String type);

}
