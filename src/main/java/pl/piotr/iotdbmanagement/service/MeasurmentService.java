package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.Measurment;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Place;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.repository.MeasurmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurmentService {

    private MeasurmentRepository repository;

    @Autowired
    public MeasurmentService(MeasurmentRepository repository) {
        this.repository = repository;
    }

    public List<Measurment> findAll() {
        return repository.findAll();
    }

    public Optional<Measurment> find(UUID id) {
        return repository.findById(id);
    }

    public List<Measurment> findAllByPlace(Place place) {
        return repository.findAllByPlace(place);
    }

    public List<Measurment> findAllByMeasurmentDate(MeasurmentDate measurmentDate) {
        return repository.findAllByMeasurmentDate(measurmentDate);
    }

    public List<Measurment> findAllBySensor(Sensor sensor) {
        return repository.findAllBySensor(sensor);
    }

    @Transactional
    public Measurment create(Measurment measurment) {
        return repository.save(measurment);
    }

}
