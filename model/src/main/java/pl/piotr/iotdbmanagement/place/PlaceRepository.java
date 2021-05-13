package pl.piotr.iotdbmanagement.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByDescription(String description);

    List<Place> findAllByDescription(String description);

}
