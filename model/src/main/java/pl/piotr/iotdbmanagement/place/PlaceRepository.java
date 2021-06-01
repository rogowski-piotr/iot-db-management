package pl.piotr.iotdbmanagement.place;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {

    Optional<Place> findByDescription(String description);

    List<Place> findAllByDescription(String description);

}
