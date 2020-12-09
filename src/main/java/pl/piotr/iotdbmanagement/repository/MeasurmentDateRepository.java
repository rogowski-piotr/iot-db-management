package pl.piotr.iotdbmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;

import java.util.UUID;

@Repository
public interface MeasurmentDateRepository extends JpaRepository<MeasurmentDate, UUID> {
}
