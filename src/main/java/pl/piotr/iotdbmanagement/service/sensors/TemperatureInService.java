package pl.piotr.iotdbmanagement.service.sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotr.iotdbmanagement.entity.sensors.TemperatureIn;
import pl.piotr.iotdbmanagement.repository.sensors.TemperatureInRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TemperatureInService {

    private TemperatureInRepository repository;

    @Autowired
    public TemperatureInService(TemperatureInRepository repository) {
        this.repository = repository;
    }

    public List<TemperatureIn> findAll() {
        return repository.findAll();
    }

    public Optional<TemperatureIn> find(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public TemperatureIn create(TemperatureIn temperatureIn) {
        return repository.save(temperatureIn);
    }

}
