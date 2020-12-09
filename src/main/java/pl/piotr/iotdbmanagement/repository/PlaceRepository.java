package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotr.iotdbmanagement.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
}
