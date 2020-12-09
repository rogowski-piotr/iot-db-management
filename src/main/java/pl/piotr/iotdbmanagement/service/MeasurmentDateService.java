package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.MeasurmentDate;
import pl.piotr.iotdbmanagement.entity.Sensor;
import pl.piotr.iotdbmanagement.repository.MeasurmentDateRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurmentDateService {

    private MeasurmentDateRepository repository;

    @Autowired
    public MeasurmentDateService(MeasurmentDateRepository repository) {
        this.repository = repository;
    }

    public List<MeasurmentDate> findAll() {
        return repository.findAll();
    }

    public List<MeasurmentDate> findAllBySensor(Sensor sensor) {
        return repository.findAllBySensor(sensor);
    }

    public Optional<MeasurmentDate> find(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public MeasurmentDate create(MeasurmentDate measurmentDate) {
        return repository.save(measurmentDate);
    }

}
