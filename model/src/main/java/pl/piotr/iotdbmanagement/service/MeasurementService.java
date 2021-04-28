package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.Measurement;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.enums.MeasurementType;
import pl.piotr.iotdbmanagement.repository.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurementService {

    private MeasurementRepository repository;

    @Autowired
    public MeasurementService(MeasurementRepository repository) {
        this.repository = repository;
    }

    public List<Measurement> findAll() {
        return repository.findAll();
    }

    public Optional<Measurement> find(UUID id) {
        return repository.findById(id);
    }

    public List<Measurement> findAllByPlace(Place place) {
        return repository.findAllByPlace(place);
    }

    public List<Measurement> findAllByMeasurmentDate(LocalDateTime dateTime) {
        return repository.findAllByDate(dateTime);
    }

    public List<Measurement> findAllBySensor(Sensor sensor) {
        return repository.findAllBySensor(sensor);
    }

    public List<Measurement> findAllBySensor_MeasurementType(MeasurementType measurementType) {
        return repository.findAllBySensor_MeasurementType(measurementType);
    }

    @Transactional
    public Measurement create(Measurement measurement) {
        return repository.save(measurement);
    }

}
